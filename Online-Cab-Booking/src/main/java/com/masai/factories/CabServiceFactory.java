package com.masai.factories;

import com.masai.services.cabdriver.CabDriverService;
import com.masai.services.tripdetailsservice.TripDetailsService;

public interface CabServiceFactory {
    CabDriverService createCabDriverService();

    TripDetailsService createTripDetailsService();
}
