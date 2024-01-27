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
import com.credit.check.model.Payment;

@RestController
@RequestMapping(value = "/payments")
public class PaymentsAggregatorController {

	Log LOGGER = LogFactory.getLog(PaymentsAggregatorController.class);
	
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping(value = "/aggregator", produces="application/json", consumes="application/json")
	public ResponseEntity<Payment>  paymentsReport() throws JsonProcessingException
	{		
		LOGGER.info("\n---- GET Payments Aggregator initiated");
		
		Payment payment = new Payment();
		payment.setCitizenship("USA");
		payment.setSource("Payment Aggregator response : has SENSITIVE PCI");
		payment.setFirstName("Sam");
		payment.setLastName("Markson");
		payment.setMaritalStatus("M");
		payment.setCurrentResidenceCountry("GB");
		payment.setCreditcardnumber("5242677622358871");		
		
		LOGGER.info("\n---- Payments Aggregator completed");		
		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}	
}