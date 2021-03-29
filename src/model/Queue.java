package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import gui.GUI;

public class Queue extends Thread
{
	private Vector<Customer> customersInQueue; // Storing customers in a Vector because the Vector class is synchronized(lets only one Thread to operate on it at a time.
	private int id;
	private int totalQueueTime;
	private int simulationTime;
	
	public Queue(int id)
	{
		this.id = id;
		this.totalQueueTime = 0;
		customersInQueue = new Vector<Customer>();
	}
	
	public synchronized void addCustomer(Customer customer)
	{
		
		customersInQueue.add(customersInQueue.size(), customer);
		this.updateTotalQueueTime();
	}
	
	public synchronized void removeCustomer()
	{
		customersInQueue.remove(0);
		this.updateTotalQueueTime();
	}
	
	public void setSimulationTime(int a)
	{
		this.simulationTime = a;
	}
	
	public void updateTotalQueueTime()
	{
		this.totalQueueTime = 0;
		for(Customer c : this.customersInQueue)
		{
			this.totalQueueTime += c.getServiceTime();
		}
	}
	
	public synchronized void printQueueToTextArea()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String s = "";
		s = "\nCurrent time: " + dtf.format(now) + "\n";
		GUI.updateLogTextArea(s);
		
		s = "Queue #"+ this.id + "\n";
		GUI.updateLogTextArea(s);

		for(Customer c : this.customersInQueue)
		{
			s = "Customer #" + c.getId() + "     Service Time:" + c.getServiceTime() + "\n";
			GUI.updateLogTextArea(s);
		}
		
		s = "Total time of queue:" + this.totalQueueTime + "\n";
		GUI.updateLogTextArea(s);
	}
	
	public void run()
	{	
		long currentTime = System.currentTimeMillis();
		long endTime = currentTime + this.simulationTime * 1000; 
		
		while(true)
		{
			if(System.currentTimeMillis() > endTime && customersInQueue.isEmpty())
			{
				break;
			}
						
			if(!customersInQueue.isEmpty())
			{
				try
				{
					Thread.sleep(customersInQueue.elementAt(0).getServiceTime() * 1000);
					removeCustomer();
					GUI.updateQueueTextField(this);
					String s ="\nRemoved customer from Queue #" + this.getID() + "\n";
					GUI.updateLogTextArea(s);
					printQueueToTextArea();
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}	
		}
	}
	
	public int getTotalQueueTime()
	{
		return this.totalQueueTime;
	}
	
	public int getCustomersNumber()
	{
		return this.customersInQueue.size();
	}
	
	public int getID()
	{
		return this.id;
	}
}
