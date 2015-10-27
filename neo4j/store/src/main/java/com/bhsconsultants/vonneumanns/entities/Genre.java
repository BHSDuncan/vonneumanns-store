package com.bhsconsultants.vonneumanns.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Genre {

	@GraphId
	private Long nodeId;
	
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
