package com.bhsconsultants.vonneumanns.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Genre {

	@GraphId
	private Long nodeId;
	
	@Indexed
	private String genreId;
	
	private String name;

	// methods
	public Genre()
	{
		
	} // constructor
	
	public Genre(String genreId)
	{
		this.genreId = genreId;
	} // constructor
	
	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
