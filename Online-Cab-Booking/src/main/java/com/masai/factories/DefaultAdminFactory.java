package com.masai.factories;

import com.masai.entities.Admin;
import com.masai.entities.TripDetails;

public class DefaultAdminFactory implements AdminFactory {

    @Override
    public Admin createAdmin() {
        // Return a new Admin instance
        return new Admin();
    }

    @Override
    public TripDetails createTripDetails() {
        // Return a new TripDetails instance
        return new TripDetails();
    }
}
