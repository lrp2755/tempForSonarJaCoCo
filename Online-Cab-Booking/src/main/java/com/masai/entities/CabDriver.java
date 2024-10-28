package com.masai.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CabDriver extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer driverId;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "cabDriver")
	@JsonIgnore
	private Cab cab;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cabDriver", orphanRemoval = true)
	@JsonIgnore
	private List<TripDetails> tripDetailsList = new ArrayList<>();

	@NotNull(message = "License Number cannot be null")
	@Column(unique = true)
	private String licenseNumber;

	private Boolean availablity = true;

	// Default constructor
	public CabDriver() {
	}

	// Parameterized constructor to match the factory method
	public CabDriver(String username, String password, String address, String mobile, String email,
			String licenseNumber, Cab cab) {
		super.setUsername(username);
		super.setPassword(password);
		super.setAddress(address);
		super.setMobile(mobile);
		super.setEmail(email);
		this.licenseNumber = licenseNumber;
		this.cab = cab;
	}

	// Getters and Setters

	public Boolean getAvailablity() {
		return availablity;
	}

	public void setAvailablity(Boolean availablity) {
		this.availablity = availablity;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public List<TripDetails> getTripDetailsList() {
		return tripDetailsList;
	}

	public void setTripDetailsList(List<TripDetails> tripDetailsList) {
		this.tripDetailsList = tripDetailsList;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Cab getCab() {
		return cab;
	}

	public void setCab(Cab cab) {
		this.cab = cab;
	}

	@Override
	public String toString() {
		return "CabDriver [driverId=" + driverId + ", tripDetailsList=" + tripDetailsList + ", licenseNumber="
				+ licenseNumber + ", availablity=" + availablity + "]";
	}
}
