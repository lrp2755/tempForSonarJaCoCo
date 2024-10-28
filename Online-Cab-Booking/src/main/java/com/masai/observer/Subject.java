package com.masai.observer;

public interface Subject {
    //methods to register and unregister observers
	public void register(Observer observer);
	public void remove(Observer observer);
	public void notifyObservers(String status);
	
}
