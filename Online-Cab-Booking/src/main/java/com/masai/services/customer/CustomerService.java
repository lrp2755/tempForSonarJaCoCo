package com.masai.services.customer;
import com.masai.entities.Customer;

import org.springframework.http.ResponseEntity;

public interface CustomerService {
	public ResponseEntity<Customer> insertCustomer(Customer customer);
	public ResponseEntity<Customer> updateCustomer(Customer customer,String user,String pass);
	public ResponseEntity<String> deleteCustomer(Customer customer);
}
