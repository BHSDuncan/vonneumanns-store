package com.bhsconsultants.vonneumanns.repositories;

import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.bhsconsultants.vonneumanns.entities.Game;

public interface GameRepository extends GraphRepository<Game> {
	Game findByGameId(String id);
	
	@Query("MATCH (g1:Game {gameId: {0} }), (g2:Game {gameId: {1} }), p = shortestPath( (g1)-[*]-(g2) ) RETURN p")
	Iterable<Map<String, Object> > getPath(String game1Id, String game2Id);
}
