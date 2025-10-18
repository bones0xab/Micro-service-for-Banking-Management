package com.example.microservice.Repos;

import com.example.microservice.JPA.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {




    
}
