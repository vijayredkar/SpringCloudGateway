package com.credit.check.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.credit.check.model.Customer;
import com.credit.check.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/view")
public class ClientViewController {

	Log LOGGER = LogFactory.getLog(ClientViewController.class);
	
	@Autowired
	ObjectMapper mapper;
	 
	@GetMapping(value = "/payments/report", consumes="application/json", produces="application/json")
	public ResponseEntity<Payment>  customerPaymentsView() throws JsonProcessingException
	{		
		LOGGER.info("\n---- Payments View initiated");
		RestTemplate rst = new RestTemplate();
		
		String url = "http://bank-next-api-gateway:8888/payments/aggregator";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		
		

		/*
		//-- to call payment aggregator directly        - 8081  [not recommended]
	  	 ResponseEntity<Payment> response = restTemplate.exchange("http://localhost:8081/payments/aggregator", HttpMethod.GET, httpEntity, Payment.class);
		*/
		//-- to call payment aggregator via scg gateway - 8080 [recommended]
		ResponseEntity<Payment> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Payment.class);
				
		
		
		LOGGER.info("\n----  Payments View completed");		
		return new ResponseEntity<Payment>(response.getBody(), HttpStatus.OK);
	}	
	
	
	@GetMapping(value = "/customers/report", consumes="application/json", produces="application/json")
	public ResponseEntity<Customer>  customersView() throws JsonProcessingException
	{		
		LOGGER.info("\n---- Customers View initiated");
		RestTemplate rst = new RestTemplate();
		
		String url = "http://bank-next-api-gateway:8888/customers/aggregator";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		
		

		/*
		//-- to call payment aggregator directly        - 8082  [not recommended]
	  	 ResponseEntity<Payment> response = restTemplate.exchange("http://localhost:8081/customers/aggregator", HttpMethod.GET, httpEntity, Customer.class);
		*/
		//-- to call payment aggregator via scg gateway - 8080 [recommended]
		ResponseEntity<Customer> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Customer.class);
				
		
		
		LOGGER.info("\n----  Customers View completed");		
		return new ResponseEntity<Customer>(response.getBody(), HttpStatus.OK);
	}
	
	
}