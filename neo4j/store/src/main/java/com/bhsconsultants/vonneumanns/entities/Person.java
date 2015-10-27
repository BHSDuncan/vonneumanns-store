package com.bhsconsultants.vonneumanns.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Person {
	@GraphId
	private Long nodeId;
	
	private String personId;

	private String name;
	private String role;

	@Relationship(type = "WORKED_ON", direction = Relationship.OUTGOING)
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
