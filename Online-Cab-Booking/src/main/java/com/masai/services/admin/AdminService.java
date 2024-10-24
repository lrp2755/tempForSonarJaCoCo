package com.masai.services.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.masai.entities.Admin;
import com.masai.entities.TripDetails;

public interface AdminService {

	ResponseEntity<Admin> insertAdmin(Admin admin);

	ResponseEntity<Admin> updateAdmin(Admin admin, String user, String pass);

	ResponseEntity<String> deleteAdmin(Admin admin);

	ResponseEntity<List<TripDetails>> getAllTrips(Admin admin);

	ResponseEntity<List<TripDetails>> getAllTripsByCab(Admin admin, Integer cabId);

	ResponseEntity<List<TripDetails>> getAllTripsByCustomer(Admin admin, String username);
}
