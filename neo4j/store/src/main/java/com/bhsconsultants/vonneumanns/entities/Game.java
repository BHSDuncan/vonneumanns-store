package com.bhsconsultants.vonneumanns.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.ogm.annotation.Relationship;

public class Game extends Stock {
	
	private String gameId;
	
	// game attributes
	private String summary;
	private Date releaseDate;
	private String thumbImageUrl;
	private String screenThumbImageUrl;
	private String detailsUrl;

	// game relationships
	@Relationship(type = "PUBLISHED_ON")
	private Set<Platform> publishedOn;

	@Relationship(type = "SIMILAR_GAMES", direction = Relationship.UNDIRECTED)
	private Set<Game> similarGames = new HashSet<Game>();
	
	//@RelatedTo(type = "IS_IN_GENRE", elementClass = Genre.class)
	@Relationship(type = "IS_IN_GENRE")
	private Set<Genre> genres = new HashSet<Genre>();
	
	@Relationship(type = "WORKED_ON", direction = Relationship.INCOMING)
	private Iterable<Person> contributors;
	
	public Game()
	{
				
	}
	
	public Game(String gameId)
	{
		this.setGameId(gameId);
	}
	
	public Game(String title, String description, int quantity, float price)
	{
		super(title, description, quantity, price);
		this.setGameId("");
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getThumbImageUrl() {
		return thumbImageUrl;
	}

	public void setThumbImageUrl(String thumbImageUrl) {
		this.thumbImageUrl = thumbImageUrl;
	}

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

	public String getScreenThumbImageUrl() {
		return screenThumbImageUrl;
	}

	public void setScreenThumbImageUrl(String screenThumbImageUrl) {
		this.screenThumbImageUrl = screenThumbImageUrl;
	}

	public Set<Platform> getPlatformsPublishedOn() {
		return publishedOn;
	}
	
	public Set<Game> getSimilarGames() {
		return similarGames;
	}
	
	public void addSimilarGame(Game game) 
	{
		this.similarGames.add(game);
	}		

	public Set<Genre> getGenres() {
		return genres;
	}
	
	public void belongsToGenre(Genre genre) 
	{
		this.genres.add(genre);
	}		

	public Iterable<Person> getContributors() {
		return contributors;
	}
}
