package com.bhsconsultants.vonneumanns.entities;

import org.neo4j.graphdb.Direction;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.bhsconsultants.vonneumanns.exceptions.InsufficientQuantityException;

@NodeEntity
public abstract class Stock {
	@GraphId
	Long nodeId;
	
	//@Indexed(indexType = IndexType.FULLTEXT, indexName = "titleSearch")
	private String title;
	
	//@Indexed(indexType = IndexType.FULLTEXT, indexName = "descriptionSearch")
	private String description;
	private int quantity;
	private float price;

	@Relationship(type = "PURCHASED", direction = Relationship.INCOMING)
	//@Fetch
    Iterable<Purchase> purchases;
	
	// methods
	public Stock()
	{
		
	}
	
	protected Stock(String title, String description, int quantity, float price)
	{
		this.setTitle(title);
		this.setDescription(description);
		this.setQuantity(quantity);
		this.setPrice(price);
	}
	
	public void purchase(int qty) throws InsufficientQuantityException
	{
		if (this.quantity - qty >= 0)
		{
			this.quantity -= qty;
		}
		else
		{
			throw new InsufficientQuantityException("Not enough of this stock is available to purchase!");
		} // if
	}
	
	// getters and setters
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
}
