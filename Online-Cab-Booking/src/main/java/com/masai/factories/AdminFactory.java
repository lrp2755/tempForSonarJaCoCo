package com.masai.factories;

import com.masai.entities.Admin;
import com.masai.entities.TripDetails;

public interface AdminFactory {
    Admin createAdmin(String username, String password); 
    TripDetails createTripDetails(); // Include this if you need it
}
