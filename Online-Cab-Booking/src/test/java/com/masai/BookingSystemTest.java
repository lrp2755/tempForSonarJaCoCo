package com.masai;

import com.masai.observers.CustomerObserver;
import com.masai.observers.DriverObserver;
import com.masai.services.BookingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OnlineCabBookingApplicationTests {

    private BookingSystem bookingSystem;
    private CustomerObserver customerObserver;
    private DriverObserver driverObserver;

    @BeforeEach
    public void setUp() {
        bookingSystem = new BookingSystem();
        customerObserver = new CustomerObserver("John Doe");
        driverObserver = new DriverObserver("Jane Smith");
        bookingSystem.register(customerObserver);
        bookingSystem.register(driverObserver);
    }

    @Test
    void contextLoads() {}

    @Test
    public void testRegister() {
        bookingSystem.register(new CustomerObserver("Alice"));
        System.out.println("Testing register: Expected 3 observers, got " + bookingSystem.getObservers().size());
        assertEquals(3, bookingSystem.getObservers().size());
    }

    @Test
    public void testRemove() {
        bookingSystem.remove(customerObserver);
        System.out.println("Testing remove: Expected 1 observer, got " + bookingSystem.getObservers().size());
        assertEquals(1, bookingSystem.getObservers().size());
    }

    @Test
    public void testNotifyObservers() {
        bookingSystem.setStatus("Booking confirmed");
        System.out.println("Testing notifyObservers: CustomerObserver status is " + customerObserver.getStatus());
        System.out.println("Testing notifyObservers: DriverObserver status is " + driverObserver.getStatus());
        assertTrue(customerObserver.getStatus().contains("Booking confirmed"));
        assertTrue(driverObserver.getStatus().contains("Booking confirmed"));
    }

    @Test
    public void testMultipleNotifications() {
        bookingSystem.setStatus("Booking confirmed");
        bookingSystem.setStatus("Booking canceled");
        System.out.println("Testing multiple notifications: CustomerObserver status is " + customerObserver.getStatus());
        System.out.println("Testing multiple notifications: DriverObserver status is " + driverObserver.getStatus());
        assertTrue(customerObserver.getStatus().contains("Booking canceled"));
        assertTrue(driverObserver.getStatus().contains("Booking canceled"));
    }

    @Test
    public void testObserverStatusUpdate() {
        bookingSystem.setStatus("Initial status");
        System.out.println("Testing observer status update: Initial CustomerObserver status is " + customerObserver.getStatus());
        System.out.println("Testing observer status update: Initial DriverObserver status is " + driverObserver.getStatus());
        assertEquals("Initial status", customerObserver.getStatus());
        assertEquals("Initial status", driverObserver.getStatus());

        bookingSystem.setStatus("Updated status");
        System.out.println("Testing observer status update: Updated CustomerObserver status is " + customerObserver.getStatus());
        System.out.println("Testing observer status update: Updated DriverObserver status is " + driverObserver.getStatus());
        assertEquals("Updated status", customerObserver.getStatus());
        assertEquals("Updated status", driverObserver.getStatus());
    }
}
