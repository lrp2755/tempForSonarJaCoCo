package com.masai.services.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.masai.entities.Admin;
import com.masai.entities.TripDetails;
import com.masai.exceptions.UserDoesNotExist;
import com.masai.factories.AdminFactory;
import com.masai.repository.AdminRepository;
import com.masai.repository.TripDetailsRepository;
import com.masai.utils.AdminValidator;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminDao;

	@Autowired
	private AdminFactory adminFactory;

	@Autowired
	private TripDetailsRepository tripDetailsDao;

	@Override
	public ResponseEntity<Admin> insertAdmin(Admin admin) {
		Admin existingAdmin = adminDao.findByUsername(admin.getUsername());
		if (existingAdmin != null) {
			throw new UserDoesNotExist("Username already exists");
		}
		// Use the factory to create a new Admin instance
		Admin newAdmin = adminFactory.createAdmin(admin.getUsername(), admin.getPassword());
		adminDao.save(newAdmin);
		return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Admin> updateAdmin(Admin admin, String user, String pass) {
		Admin existingAdmin = adminDao.findByUsernameAndPassword(user, pass);
		if (existingAdmin == null) {
			throw new UserDoesNotExist("Username or Password is wrong");
		}

		// Update necessary fields
		if (admin.getUsername() != null)
			existingAdmin.setUsername(admin.getUsername());
		if (admin.getPassword() != null)
			existingAdmin.setPassword(admin.getPassword());
		adminDao.save(existingAdmin);

		return new ResponseEntity<>(existingAdmin, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteAdmin(Admin admin) {
		AdminValidator.validateCredentials(admin, adminDao);
		adminDao.delete(admin);
		return new ResponseEntity<>("Admin deleted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TripDetails>> getAllTrips(Admin admin) {
		AdminValidator.validateCredentials(admin, adminDao);
		List<TripDetails> trips = tripDetailsDao.findAll();
		return new ResponseEntity<>(trips, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TripDetails>> getAllTripsByCab(Admin admin, Integer cabId) {
		// Logic for fetching trips by cab
		return null;
	}

	@Override
	public ResponseEntity<List<TripDetails>> getAllTripsByCustomer(Admin admin, String username) {
		// Logic for fetching trips by customer
		return null;
	}
}
