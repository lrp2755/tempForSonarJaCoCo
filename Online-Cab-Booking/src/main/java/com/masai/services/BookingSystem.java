package com.masai.services;

import com.masai.observer.Observer;
import com.masai.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class BookingSystem implements Subject {
    private List<Observer> observers;
    private String status;

    public BookingSystem() {
        observers = new ArrayList<>();
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String status) {
        for (Observer observer : observers) {
            observer.update(status);
        }
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers(status);
    }

    public List<Observer> getObservers() {
        return this.observers;
    }
    
    public String getStatus() {
        return this.status;
    }
}