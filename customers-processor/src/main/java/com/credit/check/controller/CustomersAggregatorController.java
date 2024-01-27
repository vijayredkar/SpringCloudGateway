package com.credit.check.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.credit.check.model.Customer;

@RestController
@RequestMapping(value = "/customers")
public class CustomersAggregatorController {

	Log LOGGER = LogFactory.getLog(CustomersAggregatorController.class);
	
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping(value = "/aggregator", produces="application/json", consumes="application/json")
	public ResponseEntity<Customer>  customersReport() throws JsonProcessingException
	{		
		LOGGER.info("\n---- Customers Aggregator initiated");
		
		Customer customer = new Customer();
		customer.setCitizenship("FRA");
		customer.setSource("Customer Aggregator response : has SENSITIVE PII");
		customer.setFirstName("Peter");
		customer.setLastName("Markel");
		customer.setEmailAddress("peter_m@gmail.com");
		customer.setTelephoneNumber("+826785438752");		
		
		LOGGER.info("\n---- Customers Aggregator completed");		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}	
}