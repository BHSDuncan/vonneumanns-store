package com.bhsconsultants.vonneumanns.entities;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.neo4j.graphdb.Direction;

import com.bhsconsultants.vonneumanns.entities.Purchase;
import com.bhsconsultants.vonneumanns.entities.Stock;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@NodeEntity
public class Customer {
	@GraphId
	Long id;
	
	private String username;
	
	@Indexed
	private String firstName;
	
	@Indexed
	private String lastName;
	private String email;
	
	@RelatedTo(type = "FRIEND", direction = Direction.BOTH)
	@Fetch
    Set<Customer> friends;
	
	@RelatedToVia(type = "PURCHASED", direction = Direction.OUTGOING, elementClass = Purchase.class)
	@Fetch
    Iterable<Purchase> purchases;

	// methods
	public void addFriend(Customer friend) {
		if (this.friends == null)
			 this.friends = new LinkedHashSet<Customer>();
		
		this.friends.add(friend);
	}
	
	public Purchase makePurchase(Neo4jOperations template, Stock item, int quantity)
	{
		final Purchase purchase = template.createRelationshipBetween(this, item, Purchase.class, "PURCHASED", false); //.make(item, quantity);
		Purchase toReturn = null;
		
		if (purchase != null)
			toReturn = template.save(purchase);
		
		return toReturn;
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
