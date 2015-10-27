package com.bhsconsultants.vonneumanns.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.bhsconsultants.vonneumanns.entities.Game;

public interface GameRepository extends GraphRepository<Game> {
	/*
	@Query("START n=node:__types__(className='com.bhsconsultants.vonneumanns.entities.Game') RETURN COLLECT(n)")
	GameData getAllGames();
	*/
	Game findByGameId(String id);
	/*
	@Query("START n=node(1), x=node(97) MATCH p = shortestPath( n-[*]-x ) RETURN p")
	EndResult<EntityPath<Game, Game> > getPath();
	//EntityPath<Game, Game> getPath();
	//Collection<Node> getPath();
*/
	
		/*
	@MapResult
	public interface GameData
	{
		@ResultColumn("collect(n)")
		Iterable<Game> getGames();	
	}
	*/
}
