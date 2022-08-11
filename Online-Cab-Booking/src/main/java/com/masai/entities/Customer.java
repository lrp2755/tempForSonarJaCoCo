package com.masai.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;
	
	
	
	@OneToMany(cascade = CascadeType.ALL)
	List<TripDetails> tripDetailsList = new ArrayList<>();



	public Integer getCustomerId() {
		return customerId;
	}



	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}



	public List<TripDetails> getTripDetailsList() {
		return tripDetailsList;
	}



	public void setTripDetailsList(List<TripDetails> tripDetailsList) {
		this.tripDetailsList = tripDetailsList;
	}
	
	
}