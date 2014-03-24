package com.bhsconsultants.vonneumanns.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Person {
	@GraphId
	private Long nodeId;
	
	@Indexed
	private String personId;

	private String name;
	private String role;

	@RelatedTo(type = "WORKED_ON", elementClass = Game.class, direction = Direction.OUTGOING)
	private Set<Game> workedOn = new HashSet<Game>();

	// methods
	public Person()
	{
		
	} // constructor
	
	public Person(String personId)
	{
		this.personId = personId;
	} // constructor

	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Game> getGamesWorkedOn() {
		return workedOn;
	}

	public void workedOn(Game game) {
		this.workedOn.add(game);
	}
}
