package model;

import java.util.Random;


public class Customer
{
	private int serviceTime;
	private int id;
	
	public Customer(int clientID, int minServiceTime, int maxServiceTime)
	{	
		this.serviceTime = generateServiceTime(minServiceTime, maxServiceTime);
		this.id = clientID;
	}
	
	// Tested. Works as intended. gives number in the interval [minServiceTime, maxServiceTime]
	private synchronized int generateServiceTime(int minServiceTime, int maxServiceTime)
	{
		int randomGeneratedServiceTime;
		Random randomServiceTimeGenerator = new Random();
		randomGeneratedServiceTime = randomServiceTimeGenerator.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
		return randomGeneratedServiceTime;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public synchronized int getServiceTime()
	{
		return this.serviceTime;
	}
	
	

}
