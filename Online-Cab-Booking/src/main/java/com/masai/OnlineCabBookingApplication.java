package com.masai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.masai.services.BookingSystem;
import com.masai.observers.CustomerObserver;
import com.masai.observers.DriverObserver;

@SpringBootApplication
@EnableSwagger2
public class OnlineCabBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineCabBookingApplication.class, args);

        // Initialize BookingSystem
        BookingSystem bookingSystem = new BookingSystem();

        // Create and register observers
        CustomerObserver customerObserver = new CustomerObserver("John Doe");
        DriverObserver driverObserver = new DriverObserver("Jane Smith");

        bookingSystem.register(customerObserver);
        bookingSystem.register(driverObserver);

        // Change the status
        bookingSystem.setStatus("Booking confirmed");
    }
}
