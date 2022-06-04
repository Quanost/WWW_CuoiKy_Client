package com.se.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.se.spring.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private RestTemplate restTemplate;
	private String crmRestUrl = "http://localhost:8081/On1_Server/api/customers";
	

	@Override
	public List<Customer> getAllCustomers() {
//		return restTemplate.exchange(
//						crmRestUrl, 
//						HttpMethod.GET,
//						null,
//						new ParameterizedTypeReference<List<Customer>>() {}
//						)
//						.getBody();
		
		ResponseEntity<List> resp = restTemplate.getForEntity(crmRestUrl, List.class);
		
		List<Customer> customers = resp.getBody();
		
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		int id = customer.getId();
		
		if (id == 0) {
			restTemplate.postForEntity(crmRestUrl, customer, String.class);
		}
		else {
			restTemplate.put(crmRestUrl, customer);
		}
	}

	@Override
	public Customer getCustomerById(int id) {
		return restTemplate.getForObject(
				crmRestUrl + "/" + id, 
				Customer.class);
	}

	@Override
	public void deleteCustomer(int id) {
		restTemplate.delete(crmRestUrl + "/" + id);
	}
	
	
}
