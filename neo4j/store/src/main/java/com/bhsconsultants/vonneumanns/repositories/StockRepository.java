package com.bhsconsultants.vonneumanns.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import com.bhsconsultants.vonneumanns.entities.Stock;


public interface StockRepository extends GraphRepository<Stock>, RelationshipOperationsRepository<Stock> {
	Stock findByNodeId(String id);
}
