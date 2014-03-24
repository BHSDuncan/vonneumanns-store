package com.bhsconsultants.vonneumanns.repositories;

import java.util.Collection;

import org.neo4j.graphdb.Node;
import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.core.EntityPath;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import com.bhsconsultants.vonneumanns.entities.Game;

public interface GameRepository extends GraphRepository<Game>, RelationshipOperationsRepository<Game> {
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
