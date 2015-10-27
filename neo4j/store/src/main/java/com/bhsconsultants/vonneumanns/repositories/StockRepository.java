package com.bhsconsultants.vonneumanns.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.bhsconsultants.vonneumanns.entities.Stock;

public interface StockRepository extends GraphRepository<Stock> {
	Stock findByNodeId(String id);
}
