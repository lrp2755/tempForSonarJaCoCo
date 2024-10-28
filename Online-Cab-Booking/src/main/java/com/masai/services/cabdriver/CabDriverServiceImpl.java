package com.masai.services.cabdriver;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.masai.entities.Cab;
import com.masai.entities.CabDriver;
import com.masai.entities.CabDriverCabDTO;
import com.masai.entities.TripDetails;
import com.masai.exceptions.TripInProgress;
import com.masai.exceptions.UserDoesNotExist;
import com.masai.exceptions.UserNameAlreadyExist;
import com.masai.repository.CabDriverRepository;
import com.masai.repository.CabRepository;

@Service
public class CabDriverServiceImpl implements CabDriverService {

	@Autowired
	private CabDriverRepository cabDriverDao;

	@Autowired
	private CabRepository cabDao;

	@Override
	public ResponseEntity<CabDriver> insertCabDriver(CabDriverCabDTO cabdto) {
		validateUniqueCabDriverFields(cabdto);
		CabDriver cabDriver = createCabDriver(cabdto);
		cabDriverDao.save(cabDriver);
		return new ResponseEntity<>(cabDriver, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<CabDriver> updateCabDriver(CabDriverCabDTO cabdto, String user, String pass) {
		CabDriver cabDriver = getValidatedCabDriver(user, pass);
		updateCabDriverFields(cabDriver, cabdto);
		cabDriverDao.save(cabDriver);
		return new ResponseEntity<>(cabDriver, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteCabDriver(CabDriver cabDriver) {
		CabDriver cd = getValidatedCabDriver(cabDriver.getUsername(), cabDriver.getPassword());
		cabDriverDao.delete(cd);
		return new ResponseEntity<>("Driver with username: " + cabDriver.getUsername() + " deleted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updateStatus(CabDriver cabDriver) {
		CabDriver cd = getValidatedCabDriver(cabDriver.getUsername(), cabDriver.getPassword());
		ensureNoTripInProgress(cd);
		toggleAvailability(cd);
		cabDriverDao.save(cd);
		return new ResponseEntity<>("Status Updated Successfully", HttpStatus.ACCEPTED);
	}

	// Factory Method with Reduced Complexity
	private CabDriver createCabDriver(CabDriverCabDTO cabdto) {
		Cab cab = new Cab(cabdto.getCarType(), cabdto.getNumberPlate(), cabdto.getRatePerKms());
		CabDriver cabDriver = new CabDriver(cabdto.getUsername(), cabdto.getPassword(), cabdto.getAddress(),
				cabdto.getMobile(),
				cabdto.getEmail(), cabdto.getLicenseNumber(), cab);
		cab.setCabDriver(cabDriver);
		return cabDriver;
	}

	// Validate that fields are unique
	private void validateUniqueCabDriverFields(CabDriverCabDTO cabdto) {
		if (cabDriverDao.findByUsername(cabdto.getUsername()) != null)
			throw new UserNameAlreadyExist("Username already exists");
		if (cabDriverDao.findByLicenseNumber(cabdto.getLicenseNumber()) != null)
			throw new UserNameAlreadyExist("License number already registered");
		if (cabDao.findByNumberPlate(cabdto.getNumberPlate()) != null)
			throw new UserNameAlreadyExist("Number plate already registered");
	}

	private CabDriver getValidatedCabDriver(String username, String password) {
		CabDriver cabDriver = cabDriverDao.findByUsernameAndPassword(username, password);
		if (cabDriver == null)
			throw new UserDoesNotExist("Username or password is wrong");
		return cabDriver;
	}

	private void ensureNoTripInProgress(CabDriver cabDriver) {
		List<TripDetails> tripList = cabDriver.getTripDetailsList();
		if (!tripList.isEmpty() && !tripList.get(tripList.size() - 1).getStatus()) {
			throw new TripInProgress("Trip is already in progress");
		}
	}

	private void toggleAvailability(CabDriver cabDriver) {
		cabDriver.setAvailablity(!cabDriver.getAvailablity());
	}

	// Helper method to update CabDriver fields
	private void updateCabDriverFields(CabDriver cabDriver, CabDriverCabDTO cabdto) {
		if (cabdto.getUsername() != null && !cabdto.getUsername().equals(cabDriver.getUsername())) {
			if (cabDriverDao.findByUsername(cabdto.getUsername()) != null)
				throw new UserNameAlreadyExist("Username already exists");
			cabDriver.setUsername(cabdto.getUsername());
		}
		cabDriver.setPassword(cabdto.getPassword() != null ? cabdto.getPassword() : cabDriver.getPassword());
		cabDriver.setMobile(cabdto.getMobile() != null ? cabdto.getMobile() : cabDriver.getMobile());
		cabDriver.setAddress(cabdto.getAddress() != null ? cabdto.getAddress() : cabDriver.getAddress());
		if (cabdto.getLicenseNumber() != null && !cabdto.getLicenseNumber().equals(cabDriver.getLicenseNumber())) {
			if (cabDriverDao.findByLicenseNumber(cabdto.getLicenseNumber()) != null)
				throw new UserNameAlreadyExist("License number already exists");
			cabDriver.setLicenseNumber(cabdto.getLicenseNumber());
		}
		cabDriver.setEmail(cabdto.getEmail() != null ? cabdto.getEmail() : cabDriver.getEmail());

		updateCabDetails(cabDriver.getCab(), cabdto);
	}

	private void updateCabDetails(Cab cab, CabDriverCabDTO cabdto) {
		cab.setCarType(cabdto.getCarType() != null ? cabdto.getCarType() : cab.getCarType());
		if (cabdto.getNumberPlate() != null && !cabdto.getNumberPlate().equals(cab.getNumberPlate())) {
			if (cabDao.findByNumberPlate(cabdto.getNumberPlate()) != null)
				throw new UserNameAlreadyExist("Number plate already registered");
			cab.setNumberPlate(cabdto.getNumberPlate());
		}
		cab.setRatePerKms(cabdto.getRatePerKms() != null ? cabdto.getRatePerKms() : cab.getRatePerKms());
	}
}
