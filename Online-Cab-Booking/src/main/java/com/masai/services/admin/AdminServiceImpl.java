package com.masai.services.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.masai.entities.Admin;
import com.masai.entities.Cab;
import com.masai.entities.Customer;
import com.masai.entities.TripDetails;
import com.masai.exceptions.UserDoesNotExist;
import com.masai.exceptions.UserNameAlreadyExist;
import com.masai.factories.AdminFactory;
import com.masai.repository.AdminRepository;
import com.masai.repository.CabRepository;
import com.masai.repository.CustomerRepository;
import com.masai.repository.TripDetailsRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminDao;

	@Autowired
	private CustomerRepository customerDao;

	@Autowired
	private TripDetailsRepository tripDetailsDao;

	@Autowired
	private CabRepository cabDao;

	@Autowired
	private AdminFactory adminFactory; // Factory injected to create Admin objects

	@Override
	public ResponseEntity<Admin> insertAdmin(Admin admin) {
		Admin existingAdmin = adminDao.findByUsername(admin.getUsername());
		if (existingAdmin != null) {
			throw new UserNameAlreadyExist("Username already exists");
		}

		Admin newAdmin = adminFactory.createAdmin(); // Use factory to create Admin instance
		newAdmin.setUsername(admin.getUsername());
		newAdmin.setPassword(admin.getPassword());
		// Set other fields as needed for Admin

		adminDao.save(newAdmin);
		return new ResponseEntity<>(newAdmin, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<Admin> updateAdmin(Admin admin, String user, String pass) {
		Admin existingAdmin = adminDao.findByUsernameAndPassword(user, pass);

		if (existingAdmin == null) {
			throw new UserDoesNotExist("Username or Password is wrong");
		}

		if (admin.getUsername() != null) {
			Admin duplicateAdmin = adminDao.findByUsername(admin.getUsername());
			if (duplicateAdmin != null) {
				throw new UserNameAlreadyExist("Username already exists");
			}
			existingAdmin.setUsername(admin.getUsername());
		}

		// Update other fields as necessary
		if (admin.getPassword() != null)
			existingAdmin.setPassword(admin.getPassword());
		if (admin.getEmail() != null)
			existingAdmin.setEmail(admin.getEmail());
		if (admin.getAddress() != null)
			existingAdmin.setAddress(admin.getAddress());
		if (admin.getMobile() != null)
			existingAdmin.setMobile(admin.getMobile());

		adminDao.save(existingAdmin);
		return new ResponseEntity<>(existingAdmin, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteAdmin(Admin admin) {
		Admin existingAdmin = adminDao.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
		if (existingAdmin == null) {
			throw new UserDoesNotExist("Username or Password is wrong");
		}

		adminDao.delete(existingAdmin);
		return new ResponseEntity<>("Admin with username: " + admin.getUsername() + " deleted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TripDetails>> getAllTrips(Admin admin) {
		Admin existingAdmin = adminDao.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
		if (existingAdmin == null) {
			throw new UserDoesNotExist("Username or Password is wrong");
		}

		List<TripDetails> allTrips = tripDetailsDao.findAll();
		return new ResponseEntity<>(allTrips, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TripDetails>> getAllTripsByCab(Admin admin, Integer cabId) {
		Admin existingAdmin = adminDao.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
		if (existingAdmin == null) {
			throw new UserDoesNotExist("Username or Password is wrong");
		}

		Cab cab = cabDao.findById(cabId).orElseThrow(() -> new UserDoesNotExist("Cab does not exist"));
		List<TripDetails> trips = cab.getCabDriver().getTripDetailsList();
		return new ResponseEntity<>(trips, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TripDetails>> getAllTripsByCustomer(Admin admin, String username) {
		Admin existingAdmin = adminDao.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
		if (existingAdmin == null) {
			throw new UserDoesNotExist("Username or Password is wrong");
		}

		Customer customer = customerDao.findByUsername(username);
		if (customer == null) {
			throw new UserDoesNotExist("Customer does not exist");
		}

		List<TripDetails> trips = customer.getTripDetailsList();
		return new ResponseEntity<>(trips, HttpStatus.OK);
	}
}
