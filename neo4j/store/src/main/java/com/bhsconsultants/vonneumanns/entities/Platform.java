package com.bhsconsultants.vonneumanns.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Platform {
	@GraphId
	private Long nodeId;
	
	private String platformId;
	
	//@Indexed(indexType = IndexType.FULLTEXT, indexName = "platformNameSearch")
	private String name;
	
	//@Indexed(indexType = IndexType.FULLTEXT, indexName = "platformCompanySearch")
	private String company;
	
	private String description;
	private String summary;
	private String imageUrl;
	private Date releaseDate;
	private Integer installBase;
	
	@Relationship(type = "PUBLISHED_ON", direction = Relationship.INCOMING)
	private Set<Game> availableGames = new HashSet<Game>();
	
	// methods
	public Platform()
	{
				
	} // constructor

	public Platform(String platformId)
	{
		this.platformId = platformId;
	} // constructor

	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getInstallBase() {
		return installBase;
	}
	public void setInstallBase(Integer installBase) {
		this.installBase = installBase;
	}

	public Set<Game> getAvailableGames() {
		return availableGames;
	}

	public void addGame(Game game) {
		this.availableGames.add(game);
	}
	
	
}
