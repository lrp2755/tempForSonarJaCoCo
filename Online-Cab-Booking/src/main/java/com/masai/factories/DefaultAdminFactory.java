package com.masai.factories;

import com.masai.entities.Admin;
import com.masai.entities.TripDetails;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdminFactory implements AdminFactory {

    @Override
    public Admin createAdmin(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        return admin;
    }

    @Override
    public TripDetails createTripDetails() {
        return new TripDetails(); // Implement this if you need it
    }
}
