package com.bhsconsultants.vonneumanns.entities;


import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.bhsconsultants.vonneumanns.exceptions.InsufficientQuantityException;

@RelationshipEntity(type = "PURCHASED")
public class Purchase {
	@GraphId
	private Long id;
	
	@StartNode	
	private Customer customer;
	
	@EndNode	
	private Stock item;
	
	private int quantity = 0;
	
	public Purchase() {}
	
	public Purchase(Customer customer, Stock item)
	{
		this.customer = customer;
		this.item = item;
	}	

	public Purchase make(int quantity)
	{
		Purchase toReturn = null;
		
		try
		{
			this.item.purchase(quantity);
			this.quantity = quantity;
			toReturn = this;
		}
		catch (InsufficientQuantityException iqe)
		{
			System.err.println(iqe.getMessage());
		}
		catch (NullPointerException npe)
		{
			System.err.println(npe.getMessage());
		} // try
		
		return toReturn;
	}
	
	public Purchase make(Stock item, int quantity)
	{
		this.item = item;
		
		return make(quantity);
	}
	
	// getters and setters
	public int getQuantity()
	{
		return quantity;
	}
	
	public Customer getCustomer()
	{
		return this.customer;
	}
	
	public Stock getItem()
	{
		return this.item;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	
	public void setItem(Stock item)
	{
		this.item = item;
	}
}
