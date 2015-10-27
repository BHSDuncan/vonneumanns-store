package com.bhsconsultants.vonneumanns.dataimport;

import com.bhsconsultants.vonneumanns.entities.*;
import com.bhsconsultants.vonneumanns.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GameDbImportService {

    private static final Logger logger = LoggerFactory.getLogger(GameDbImportService.class);
    GameDbJsonMapper gameDbJsonMapper = new GameDbJsonMapper();

    @Autowired
    private GameRepository gameRepository;
    
    @Autowired
    private PlatformRepository platformRepository;
    
    /*
    @Autowired
    private DirectorRepository directorRepository;
*/
    @Autowired
    private GameDbApiClient client;

    @Autowired
    private GameDbLocalStorage localStorage;
    
    @Autowired private Neo4jOperations template;

    @Transactional
    public Map<Integer, String> importGames(Map<Integer, Integer> ranges) {
        final Map<Integer,String> games=new LinkedHashMap<Integer, String>();
        for (Map.Entry<Integer, Integer> entry : ranges.entrySet()) {
            for (int id = entry.getKey(); id <= entry.getValue(); id++) {
                String result = importGameFailsafe(id);
                games.put(id, result);
            }
        }
        return games;
    }

    private String importGameFailsafe(Integer id) {
        try {
            Game game = doImportGame(String.valueOf(id));
            return game.getTitle();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Transactional
    public Game importGame(String gameId) {
        return doImportGame(gameId);
    }

    private Game doImportGame(String gameId) {
        logger.debug("Importing game " + gameId);

        Game game = gameRepository.findByGameId(gameId);
        
        if (game == null) { // Not found: Create fresh
            game = new Game(gameId);
        }

        Map data = loadGameData(gameId);
        if (data.containsKey("not_found")) throw new RuntimeException("Data for Game "+gameId+" not found.");
        
        gameDbJsonMapper.mapToGame(data, game);
        
        // notice how we persist the game first before setting relationships
        gameRepository.save(game);
        
        relatePlatformsToGame(game, data);
        relateSimilarGamesToGame(game, data);
        relateGenresToGame(game, data);
        relateContributorsToGame(game, data);
        
        return game;
    }

    private Map loadGameData(String gameId) {
        if (localStorage.hasGame(gameId)) {
            return localStorage.loadGame(gameId);
        }

        Map data = client.getGame(gameId);
        localStorage.storeGame(gameId, data);
        return data;
    }

    private void relatePlatformsToGame(Game game, Map data) {
        @SuppressWarnings("unchecked") Collection<Map> platforms = (Collection<Map>) data.get("platforms");
        for (Map entry : platforms) {
            String id = "" + entry.get("id");
            Platform platform = new Platform(id);
            
            try
            {
            	//gameDbJsonMapper.mapToPlatform(entry, platform);
            	platform = this.importPlatform(id, platform);
            	
                platform.addGame(game);
                platformRepository.save(platform);
            } 
            catch (GameDbException e)
            {
                if (logger.isInfoEnabled()) logger.info("Could not add platform ID " + id + " " + entry);                        	            
            }  // catch
        }
    }

    private void relateGenresToGame(Game game, Map data) {
        @SuppressWarnings("unchecked") Collection<Map> genres = (Collection<Map>) data.get("genres");
        for (Map entry : genres) {
            String id = "" + entry.get("id");
            Genre genre = new Genre(id);
            
            try
            {
            	gameDbJsonMapper.mapToGenre(entry, genre);
            	//genre = this.importPlatform(id, platform);
            	
                game.belongsToGenre(genre);
                template.save(genre);
                //platformRepository.save(platform);
            } 
            catch (GameDbException e)
            {
                if (logger.isInfoEnabled()) logger.info("Could not add genre ID " + id + " " + entry);                        	            
            }  // catch
        }
    }

    private void relateContributorsToGame(Game game, Map data) {
        @SuppressWarnings("unchecked") Collection<Map> contributors = (Collection<Map>) data.get("people");
        for (Map entry : contributors) {
            String id = "" + entry.get("id");
            Person contributor = new Person(id);
            
            try
            {
            	gameDbJsonMapper.mapToPerson(entry, contributor);
            	//genre = this.importPlatform(id, platform);
            	
            	contributor.workedOn(game);
                template.save(contributor);
                //platformRepository.save(platform);
            } 
            catch (GameDbException e)
            {
                if (logger.isInfoEnabled()) logger.info("Could not add genre ID " + id + " " + entry);                        	            
            }  // catch
        }
    }
    
    private void relateSimilarGamesToGame(Game game, Map data) {
        @SuppressWarnings("unchecked") Collection<Map> similarGames = (Collection<Map>) data.get("similar_games");
        for (Map entry : similarGames) {
            String id = "" + entry.get("id");
            //Game newGame = new Game(id);
            Game newGame = gameRepository.findByGameId(id);

            if (newGame == null)
            {
	            try
	            {
	            	newGame = new Game(id);
	            	gameDbJsonMapper.mapToGame(entry, newGame);            	
	            } 
	            catch (GameDbException e)
	            {
	                if (logger.isInfoEnabled()) logger.info("Could not add game ID " + id + " " + entry);                        	            
	            }  // catch
            } // if
            
            newGame.addSimilarGame(game);
            gameRepository.save(newGame);
        }
    }

    @Transactional
    public Platform importPlatform(String platformId, Platform platform) {
        return doImportPlatform(platformId, platform);
    }

    private Platform doImportPlatform(String platformId, Platform newPlatform) {
        logger.debug("Importing platform " + platformId);
        
        // Bug: DATAGRAPH-453
        //Platform platform = template.lookup(Platform.class, "platformId", platformId).to(Platform.class).singleOrNull();
        
        //Platform platform = platformRepository.findBySchemaPropertyValue("platformId", platformId);
        Platform platform = platformRepository.findByPlatformId(platformId);
        
        if (platform != null) return platform;
        
        Map data = loadPlatformData(platformId);
        
        if (data.containsKey("not_found")) throw new RuntimeException("Data for Platform "+platformId+" not found.");
        gameDbJsonMapper.mapToPlatform(data, newPlatform);
        return template.save(newPlatform);
    }

    private Map loadPlatformData(String platformId) {
        if (localStorage.hasPlatform(platformId)) {
            return localStorage.loadPlatform(platformId);
        }
        Map data = client.getPlatform(platformId);
        localStorage.storePlatform(platformId, data);
        return localStorage.loadPlatform(platformId);
    }
}
