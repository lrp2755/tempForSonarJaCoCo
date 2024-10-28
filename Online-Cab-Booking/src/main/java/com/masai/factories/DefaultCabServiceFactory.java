package com.masai.factories;

import com.masai.services.cabdriver.CabDriverService;
import com.masai.services.cabdriver.CabDriverServiceImpl;
import com.masai.services.tripdetailsservice.TripDetailsService;
import com.masai.services.tripdetailsservice.TripDetailsServiceImpl;

public class DefaultCabServiceFactory implements CabServiceFactory {

    @Override
    public CabDriverService createCabDriverService() {
        // You can include additional logic here if necessary for creating the service
        return new CabDriverServiceImpl();
    }

    @Override
    public TripDetailsService createTripDetailsService() {
        // You can include additional logic here if necessary for creating the service
        return new TripDetailsServiceImpl();
    }
}
