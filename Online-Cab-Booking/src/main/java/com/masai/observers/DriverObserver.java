package com.masai.observers;

import com.masai.observer.Observer;

public class DriverObserver implements Observer {
    private String name;
    private String status;

    public DriverObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        this.status = message;
        System.out.println("Driver " + name + ": " + message);
    }

    public String getStatus() {
        return status;
    }
}
