package com.bhsconsultants.vonneumanns.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.EntityPath;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bhsconsultants.vonneumanns.entities.Game;
import com.bhsconsultants.vonneumanns.repositories.GameRepository;
//import com.bhsconsultants.vonneumanns.repositories.GameRepository.GameData;

@Controller
public class GameController {
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired 
	private Neo4jOperations template;
	
	@ModelAttribute("selectGames1")
	public List<Game> populateSelectGames1()
	{
		return new ArrayList<Game>();
	}

	@ModelAttribute("selectGames2")
	public List<Game> populateSelectGames2()
	{
		return new ArrayList<Game>();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //addUser(model);
		
		long startTime = System.currentTimeMillis();
		//GameData gamesData = gameRepository.getAllGames();
		//Iterable<Game> theGames = gamesData.getGames();
		//Iterable<Game> theGames = gameRepository.findAll();
		//Collection<Game> allGames = IteratorUtil.asCollection(theGames); // VERY SLOW
		Collection<Game> allGames = IteratorUtil.asCollection(gameRepository.findAll());
		//Collection<Game> allGames = IteratorUtil.asCollection(template.findAll(Game.class));
		//Collection<Game> allGames = new HashSet<Game>();
		//allGames.add(gameRepository.findByGameId("1"));
		
		//Collection<Node> col = gameRepository.getPath();
		//EntityPath<Game, Game> thePath = gameRepository.getPath().single();
		
		//EntityPath<Game, Game>  thePath = gameRepository.getPath();
		//EntityPath<Game, Game> singlePath = thePath;		
		//for (EntityPath<Game, Game> singlePath : thePath)
		/*
		{
			Iterable<Node> nodes= singlePath.nodes();				

			for (Node n : nodes)
			{
				String theType = (String)n.getProperty("__type__", "");
				//class<theType> entity = template.convert(n, class<theType>);
				System.out.println("Node type: " + "");			
			} // for
			
		} // for
		*/
		
		//Collection<Game> allGames = new Collection<Game>();
		//ArrayList<Game> allGames = new ArrayList<Game>();
		long endTime = System.currentTimeMillis();
		
		System.out.println("Retrieved " + String.valueOf(allGames.size()) + " games in " + String.valueOf(endTime - startTime) + " ms.");
		model.addAttribute("games", allGames);
		
        return "index";
    }
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String processSubmit(Model model, @RequestParam(value="lstGames1", required=true) Integer game1Id, @RequestParam(value="lstGames2", required=true) Integer game2Id)
	{
		System.out.println("Selected " + game1Id.toString() + " and " + game2Id.toString());
		
		return "index";
	}
}
