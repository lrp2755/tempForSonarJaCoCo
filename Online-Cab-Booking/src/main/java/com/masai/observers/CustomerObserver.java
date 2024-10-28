package com.masai.observers;

import com.masai.observer.Observer;

public class CustomerObserver implements Observer {
    private String name;
    private String status;

    public CustomerObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        this.status = message;
        System.out.println("Customer " + name + ": " + message);
    }

    public String getStatus() {
        return status;
    }
}
