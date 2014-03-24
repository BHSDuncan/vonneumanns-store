package com.bhsconsultants.vonneumanns.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bhsconsultants.vonneumanns.entities.*;
import com.bhsconsultants.vonneumanns.repositories.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/store-test-context.xml"})
@Transactional
public class EntitiesTest 
{
	@Autowired 
	Neo4jOperations template;
	
	@Autowired
	protected CustomerRepository customerRepository;
	
	@Autowired
	protected StockRepository stockRepository;
	
	// test data
	private final String C1_FNAME = "Jay";
	private final String C1_LNAME = "Jordon";
	
	private final String C2_FNAME = "Michel";
	private final String C2_LNAME = "Beaulieu";
	
	private final String C3_FNAME = "Lance";
	private final String C3_LNAME = "Hill";
	
	private final String C4_FNAME = "John";
	private final String C4_LNAME = "Seravic";
	
	private final String C5_FNAME = "Nerdy";
	private final String C5_LNAME = "McNerdson";
	
	private final String G1_TITLE = "Modern Warfare 3";
	private final String G1_DESC = "FPS mayhem!";
	private final int G1_STOCK_QTY = 50;
	private final float G1_PRICE = 69.99f;

	private final String G2_TITLE = "Diablo 3";
	private final String G2_DESC = "The sequel to Diable 2!";
	private final int G2_STOCK_QTY = 50;
	private final float G2_PRICE = 69.99f;

	private final String G3_TITLE = "Halo 3";
	private final String G3_DESC = "Everyone knows what Halo is!";
	private final int G3_STOCK_QTY = 30;
	private final float G3_PRICE = 49.99f;

	private Customer c1;
	private Customer c2;
	private Customer c3;
	private Customer c4;
	private Customer c5;
	
	private Stock g1;
	private Stock g2;
	private Stock g3;
		
	@Before
	public void setUp() throws Exception 
	{
		// customers
		c1 = new Customer();
		c1.setFirstName(C1_FNAME);
		c1.setLastName(C1_LNAME);
		
		c2 = new Customer();
		c2.setFirstName(C2_FNAME);
		c2.setLastName(C2_LNAME);

		c3 = new Customer();
		c3.setFirstName(C3_FNAME);
		c3.setLastName(C3_LNAME);

		c4 = new Customer();
		c4.setFirstName(C4_FNAME);
		c4.setLastName(C4_LNAME);
		
		c5 = new Customer();
		c5.setFirstName(C5_FNAME);
		c5.setLastName(C5_LNAME);

		customerRepository.save(c1);
		customerRepository.save(c2);
		customerRepository.save(c3);
		customerRepository.save(c4);
		customerRepository.save(c5);
		
		// stock
		g1 = new Game(G1_TITLE, G1_DESC, G1_STOCK_QTY, G1_PRICE);
		g2 = new Game(G2_TITLE, G2_DESC, G2_STOCK_QTY, G2_PRICE);
		g3 = new Game(G3_TITLE, G3_DESC, G3_STOCK_QTY, G3_PRICE);
		
		stockRepository.save(g1);
		stockRepository.save(g2);
		stockRepository.save(g3);		
	}
	
	@Test
	public void customerCanMakePurchases()
	{
		// setup our game constants
		final int QTY = 1;
		final String GAME_TITLE = "Space Weasel 3.5";
		final String GAME_TITLE_2 = "The Space Testing Game";
		final String GAME_DESC = "Rodent fun in space!";
		final String GAME_DESC_2 = "Tests in space!";
		final int STOCK_QTY = 10;
		final float PRICE = 59.99f;
		
		// setup our customer constants
		final String FIRST_NAME = "Edgar";
		final String LAST_NAME = "Neubauer";
		
		// create our customer for this test
		Customer customer1 = new Customer();
		
		// set the customer's properties (NOTE: "firstName" is an indexed property in the Customer entity, but "lastName" is not!)
		customer1.setFirstName(FIRST_NAME);
		customer1.setLastName(LAST_NAME);
		
		// create our games for this test
		Stock game1 = new Game(GAME_TITLE, GAME_DESC, STOCK_QTY, PRICE);
		Stock game2 = new Game(GAME_TITLE_2, GAME_DESC_2, STOCK_QTY + 5, PRICE + 5);

		// save entities BEFORE saving the relationships!
		template.save(game1);
		template.save(game2);
		template.save(customer1);

		// make those purchases! Support our test economy!
		// (NOTE: "makePurchase" actually uses the "template" parameter to persist the relationship, so no need to do it again)
		Purchase p1 = customer1.makePurchase(template, game1, QTY);		
		Purchase p2 = customer1.makePurchase(template, game2, QTY);
		
		// retrieve the customer
		Customer customer1Found = this.customerRepository.findBySchemaPropertyValue("firstName", FIRST_NAME);
		
		//
		// Tests
		//
		
		// can we find/retrieve the customer?
		assertNotNull("Unable to find customer.", customer1Found);
		
		// can we find the specific customer for which we are looking?
		assertEquals("Returned customer but not the one searched for.", FIRST_NAME, customer1Found.getFirstName());
		
		// does the retrieved customer have its non-indexed properties returned, as well?
		assertEquals("Returned customer doesn't have non-indexed properties returned.", LAST_NAME, customer1Found.getLastName());
		
		// retrieve the customer's purchases
		// (NOTE: We case as a Collection just to make checking the number of puchases easier)		
		Iterable<Purchase> purchasesIt = customer1Found.getPurchases();
		Collection<Purchase> purchases = IteratorUtil.asCollection(purchasesIt);
		
		// do we have the correct number of purchases?
		assertEquals("Number of purchases do not match.", 2, purchases.size());
		
		// go through the actual purchases...
		Iterator<Purchase> purchIt = purchasesIt.iterator();
		Purchase purchase1 = purchIt.next();
		
		// retrieving objects via Spring Data pulls lazily by default; for eager mapping, use @Fetch (but be forewarned!)
		// ...this means we have to use the fetch() method to finish loading related objects
		Stock s1 = template.fetch(purchase1.getItem());
		
		// can we retrieve our first purchase successfully w/ its details?
		assertEquals("Purchased item not persisted properly.", GAME_TITLE, s1.getTitle());

		purchase1 = purchIt.next();		
		Stock s2 = template.fetch(purchase1.getItem());
		
		// can we retrieve our second purchase successfully w/ its details?
		assertEquals("Purchased item not persisted properly.", GAME_TITLE_2, s2.getTitle());
		
		// if we're here, then all test ran succesfully.  Hooray!
	}
	
	@Test
	public void customerFriends()
	{
		// add friends
		c1.addFriend(c2);
		c1.addFriend(c3);
		c1.addFriend(c4);
		c1.addFriend(c5);

		// be careful! setting a "Direction.BOTH" relationship in one node entity will have the ENTIRE relationship saved (*including the adjoining node*) when saving just ONE of the two entities!
		// ...if you save both, Neo4j will remove the duplication (and you'll be left wondering why c1 is a friend of c2, but not vice versa)
		
		// save c1's friends
		customerRepository.save(c1);

		// we can't just continue to add friends to this.c2, as once we try to save this.c2, it'll remove the duplicate relationship between c1 and c2.
		// ...so, to get around this, we retrieve the persisted object from the DB
		//Customer c2Found = customerRepository.findByPropertyValue("lastName", C2_LNAME);
		Customer c2Found = customerRepository.findBySchemaPropertyValue("lastName", C2_LNAME); // updated for SDN 3.0.1
		c2Found.addFriend(c3);
		c2Found.addFriend(c4);
		c2Found.addFriend(c5);

		// save c2's friends, which will preserve the existing relationship with c1! Old friends can remain friends!
		customerRepository.save(c2Found);

		// retrieve c1 for some tests
		Customer c1Found = customerRepository.findBySchemaPropertyValue("lastName", C1_LNAME);
		
		Iterable<Customer> c1Friends = c1Found.getFriends();
		Collection<Customer> c1FriendsSet = IteratorUtil.asCollection(c1Friends);
		Iterator<Customer> custIt = c1Friends.iterator();
		
		int numFriends = 0;
		
		// let's make sure all of c1's friends were retrieved
		assertTrue("Friend not found.", c1FriendsSet.containsAll(IteratorUtil.asCollection(c1.getFriends())));
		
		// let's also make sure that c1 and c2 are still buds specifically (these two are inseparable...you should see them at ComicCon!)
		assertTrue("Friend not found.", c1FriendsSet.contains(c2));
		
		// let's make sure the exact number of friends returned is correct 
		while (custIt.hasNext())
		{			
			custIt.next();
			numFriends++;
		} // while
		
		assertEquals("Number of friends returned incorrect.", 4, numFriends);
	}
	
	@Test
	public void mostPurchasedGame()
	{
		c1.makePurchase(template, g1, 1);
		c2.makePurchase(template, g1, 1);
		c3.makePurchase(template, g1, 1);
		c4.makePurchase(template, g1, 1);
		c5.makePurchase(template, g1, 1);
		
		c2.makePurchase(template, g2, 1);
		c3.makePurchase(template, g2, 1);
		c4.makePurchase(template, g2, 1);
		
		c2.makePurchase(template, g3, 1);
		
		
	}
	
}
