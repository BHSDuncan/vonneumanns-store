package com.bhsconsultants.vonneumanns.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.bhsconsultants.vonneumanns.entities.Customer;

public interface CustomerRepository extends GraphRepository<Customer> {

    public Customer findByFirstName(String firstName);
    public Customer findByLastName(String lastName);
}
