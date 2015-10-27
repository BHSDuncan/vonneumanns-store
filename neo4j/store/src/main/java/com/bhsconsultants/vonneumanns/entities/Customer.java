package com.bhsconsultants.vonneumanns.entities;

import com.bhsconsultants.vonneumanns.entities.Purchase;
import com.bhsconsultants.vonneumanns.entities.Stock;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.template.Neo4jOperations;

@NodeEntity
public class Customer {
	@GraphId
	private Long id;
	
	private String username;
	
	private String firstName;
	private String lastName;
	private String email;
	
	@Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
    Set<Customer> friends;
	
	@Relationship(type = "PURCHASED", direction = Relationship.OUTGOING)
    Iterable<Purchase> purchases;
	
	// this is for testing: http://stackoverflow.com/questions/22621789/spring-data-neo4j-class-polymorphism
	Set<Stock> stockPurchased;

	// methods
	public void addFriend(Customer friend) {
		if (this.friends == null)
			 this.friends = new LinkedHashSet<Customer>();
		
		this.friends.add(friend);
	}
	
	// this is for testing: http://stackoverflow.com/questions/22621789/spring-data-neo4j-class-polymorphism
	public void addStockPurchase(Stock item)
	{
		if (this.stockPurchased == null)
			this.stockPurchased = new LinkedHashSet<Stock>();
		
		this.stockPurchased.add(item);
	}
	
	public Set<Stock> getStockPurchased()
	{
		// eagerly loaded
		return this.stockPurchased;
	}
	
	public Purchase makePurchase(Neo4jOperations template, Stock item, int quantity)
	{
		//final Purchase purchase = template.createRelationshipBetween(this, item, Purchase.class, "PURCHASED", false); //.make(item, quantity);
		final Purchase purchase = new Purchase();
		purchase.setCustomer(this);
		purchase.setItem(item);
			
		template.save(purchase);
		
		return purchase;
	}
	
	// use this to get suggestions based on what other people who bought this also got
	public Iterable<Stock> getTopNSuggestions(int n)
	{
		// TODO: Finish implementing this.
		return null;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Iterable<Customer> getFriends() {
		return friends;
	}
	
	public Iterable<Purchase> getPurchases() {
		return this.purchases;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) 
			return true;

		if (o == null || getClass() != o.getClass()) 
			return false;

        Customer customer = (Customer)o;
        
        if (id == null) 
        	return super.equals(o);
        
        return id.equals(customer.id);
	}
}
