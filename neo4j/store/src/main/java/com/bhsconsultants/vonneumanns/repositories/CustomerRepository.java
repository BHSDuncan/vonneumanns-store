package com.bhsconsultants.vonneumanns.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import com.bhsconsultants.vonneumanns.entities.Customer;

public interface CustomerRepository extends GraphRepository<Customer>, RelationshipOperationsRepository<Customer> {

	
}
