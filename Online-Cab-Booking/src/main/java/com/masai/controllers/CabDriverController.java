package com.masai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.masai.entities.CabDriver;
import com.masai.entities.CabDriverCabDTO;
import com.masai.entities.TripDetailsDTO;
import com.masai.factories.CabServiceFactory; // Updated package and interface
import com.masai.factories.DefaultCabServiceFactory; // Updated package and class
import com.masai.services.cabdriver.CabDriverService;
import com.masai.services.tripdetailsservice.TripDetailsService;

@RestController
@RequestMapping(value = "/cabdriver")
public class CabDriverController {

	private CabServiceFactory serviceFactory = new DefaultCabServiceFactory(); // Updated to use the correct factory
	private CabDriverService cabDriverService = serviceFactory.createCabDriverService();
	private TripDetailsService tripDetailsService = serviceFactory.createTripDetailsService();

	@PostMapping("/create")
	public ResponseEntity<CabDriver> insertCabDriverHandler(@RequestBody CabDriverCabDTO cabdto) {
		return cabDriverService.insertCabDriver(cabdto);
	}

	@PutMapping("/update")
	public ResponseEntity<CabDriver> updateCabDriverHandler(@RequestBody CabDriverCabDTO cabdto,
			@RequestParam String user, @RequestParam String pass) {
		return cabDriverService.updateCabDriver(cabdto, user, pass);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCabDriverHandler(@RequestBody CabDriver cabDriver) {
		return cabDriverService.deleteCabDriver(cabDriver);
	}

	@PostMapping("/tripcompleted")
	public ResponseEntity<String> tripCompletionHandler(@RequestBody TripDetailsDTO tripDto) {
		return tripDetailsService.calculateBill(tripDto);
	}

	@PostMapping("/updatestatus")
	public ResponseEntity<String> updateStatusHandler(@RequestBody CabDriver cDriver) {
		return cabDriverService.updateStatus(cDriver);
	}
}
