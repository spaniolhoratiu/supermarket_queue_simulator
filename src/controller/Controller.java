package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import gui.GUI;
import model.Customer;
import model.Queue;

public class Controller extends Thread implements Runnable
{
	public final int MAX_NUMBER_OF_QUEUES = 5;
	private int minOfIntervalOfClientsArrival;
	private int maxOfIntervalOfClientsArrival;
	private int minServiceTime;
	private int maxServiceTime;
	private int numberOfQueues;
	private int simulationTime;
	
	private ArrayList<Queue> queues;

	public Controller(int numberOfQueues)
	{
		
		if(numberOfQueues <= MAX_NUMBER_OF_QUEUES && numberOfQueues > 0)
		{
			this.numberOfQueues = numberOfQueues;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Number of queues must be >0 and <=5", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		queues = new ArrayList<Queue>();
		for(int i = 0; i < this.numberOfQueues; i++)
		{
			Queue q = new Queue(i);
			queues.add(q);
		}
	}
	
	public void setMinIntervalOfClientsArrival(int a)
	{
		this.minOfIntervalOfClientsArrival = a;
	}
	
	public void setMaxIntervalOfClientsArrival(int a)
	{
		this.maxOfIntervalOfClientsArrival = a;
	}
	
	public void setMinServiceTime(int a)
	{
		this.minServiceTime = a;
	}
	
	public void setMaxServiceTime(int a)
	{
		this.maxServiceTime = a;
	}
	
	public void setSimulationTime(int a)
	{
		this.simulationTime = a;
		for(Queue q : queues)
		{
			q.setSimulationTime(a);
		}
	}
	
	public void setNumberOfQueues(int a)
	{
		this.numberOfQueues = a;
	}
	
	private int generateRandomNumberBetween(int minTime, int maxTime)
	{
		int randomGeneratedTimeBetweenClientArrival;
		Random randomTimeGenerator = new Random();
		randomGeneratedTimeBetweenClientArrival = randomTimeGenerator.nextInt(maxTime - minTime + 1) + minTime;
		return randomGeneratedTimeBetweenClientArrival;
	}
	
	private void waitSeconds(int seconds)
	{
		try
		{
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private Queue getFastestQueue()
	{
		int min = Integer.MAX_VALUE;
		int minQueueAt = -1;
		
		for(int i = 0; i < this.numberOfQueues; i++)
		{
			if(queues.get(i).getTotalQueueTime() < min)
			{
				min = queues.get(i).getTotalQueueTime();
				minQueueAt = i;
			}
		}
		return queues.get(minQueueAt);
	}
	
	private void startAllQueues()
	{
		for(Queue q : queues)
		{
			q.start();
		}
	}
	
	private Queue getBusiestQueue()
	{
		int max = Integer.MIN_VALUE;
		int maxQueueAt = -1;
		
		for(int i = 0; i < this.numberOfQueues; i++)
		{
			if(queues.get(i).getTotalQueueTime() > max)
			{
				max = queues.get(i).getTotalQueueTime();
				maxQueueAt = i;
			}
		}
		return queues.get(maxQueueAt);
	}
	
	private String convertToHourlyFormat(long millis)
	{
		long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return hours + ":" + minutes + ":" + seconds;
	}
	
	@Override
	public void run()
	{
		long currentTime = System.currentTimeMillis();
		long endTime = currentTime + this.simulationTime * 1000;
		float totalCustomers = 0.0f;
		float totalTimeSpentAtQueues = 0.0f;
		float averageCustomerWaitingTime = 0.0f;		
		
		startAllQueues();
		
		while(System.currentTimeMillis() <= endTime)  // Run thread for 30 secs
		{
			try
			{	
				GUI.updateLogTextArea("\nThreads = " + Thread.activeCount());
				Thread.sleep(50);
				
				Random rand = new Random();
				int customerID = rand.nextInt();
				Customer c = new Customer(customerID, this.minServiceTime, this.maxServiceTime);
				waitSeconds(generateRandomNumberBetween(this.minOfIntervalOfClientsArrival,this.maxOfIntervalOfClientsArrival));
				Queue minQueue = getFastestQueue();
				
				String s = "\n\n Added #"+ c.getId() + " to Queue #" + minQueue.getID() + "\n";
				GUI.updateLogTextArea(s);
				
				minQueue.addCustomer(c);
				totalCustomers++;
				totalTimeSpentAtQueues += c.getServiceTime();
				
				Queue maxQueueNow = getBusiestQueue();
				Queue maxQueueFinal = new Queue(100);
				if(maxQueueNow.getTotalQueueTime() > maxQueueFinal.getTotalQueueTime())
				{
					maxQueueFinal = maxQueueNow;
				}
					
				
				GUI.updateQueueTextField(minQueue);
				minQueue.printQueueToTextArea();

			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String s = "\n\nCustomer generation stopped at: " + dtf.format(now) + "\n";
		GUI.updateLogTextArea(s);
		GUI.updateLogTextArea("//////// Stats:\n");
		GUI.updateLogTextArea("Total customers generated: " + totalCustomers + ".\n");
		GUI.updateLogTextArea("Total time spent at queues: " + totalTimeSpentAtQueues + " seconds.\n");
		averageCustomerWaitingTime = totalTimeSpentAtQueues / totalCustomers;
		GUI.updateLogTextArea("Average service time of 1 customer: " + averageCustomerWaitingTime + ".\n");
		//GUI.updateLogTextArea("Peak hour: " + convertToHourlyFormat(peakHour));
	
	}
	
	
}

