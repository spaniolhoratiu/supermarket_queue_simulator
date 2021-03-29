package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import controller.Controller;
import model.Queue;

public class GUI
{
	JFrame jframe;
	
	JLabel numberOfQueuesLabel;
	JTextField numberOfQueuesTextField;
	
	JLabel minIntervalOfClientsArrivalLabel;
	JTextField minIntervalOfClientsArrivalTextField;
	
	JLabel maxIntervalOfClientsArrivalLabel;
	JTextField maxIntervalOfClientsArrivalTextField;
	
	JLabel minServiceTimeLabel;
	JTextField minServiceTimeTextField;
	
	JLabel maxServiceTimeLabel;
	JTextField maxServiceTimeTextField;
	
	JLabel simulationTimeLabel;
	JTextField simulationTimeTextField;
	
	JButton startSimulationButton;
	
	static JTextField queue1TextField;
	static JTextField queue2TextField;
	static JTextField queue3TextField;
	static JTextField queue4TextField;
	static JTextField queue5TextField;
	
	JLabel queue1Label;
	JLabel queue2Label;
	JLabel queue3Label;
	JLabel queue4Label;
	JLabel queue5Label;
	
	JPanel queue1Panel;
	JPanel queue2Panel;
	JPanel queue3Panel;
	JPanel queue4Panel;
	JPanel queue5Panel;
	
	JPanel finalPanel;
	JPanel inputPanel;
	JPanel visualQueuePanel;
	JPanel leftwardPanel;
	
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	JPanel panel5;
	
	JPanel logPanel;
		
	static JTextArea logTextArea;
	JScrollPane scroll;
	
	GUI()
	{
		jframe = new JFrame("Queueing based system");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(1600, 900);
		
		numberOfQueuesLabel = new JLabel("Number of queues:");
		numberOfQueuesTextField = new JTextField();
		numberOfQueuesTextField.setPreferredSize(new Dimension(400, 25));
		panel1 = new JPanel();
		panel1.add(numberOfQueuesLabel);
		panel1.add(numberOfQueuesTextField);
		minIntervalOfClientsArrivalLabel = new JLabel("Minimum interval of client arrival:");
		minIntervalOfClientsArrivalTextField = new JTextField();
		minIntervalOfClientsArrivalTextField.setPreferredSize(new Dimension(400, 25));
		maxIntervalOfClientsArrivalLabel = new JLabel("Maximum interval of client arrival:");
		maxIntervalOfClientsArrivalTextField = new JTextField();
		maxIntervalOfClientsArrivalTextField.setPreferredSize(new Dimension(400, 25));
		
		panel2 = new JPanel();
		panel2.add(minIntervalOfClientsArrivalLabel);
		panel2.add(minIntervalOfClientsArrivalTextField);
		panel2.add(maxIntervalOfClientsArrivalLabel);
		panel2.add(maxIntervalOfClientsArrivalTextField);
		
		minServiceTimeLabel = new JLabel("Minimum service time:");
		minServiceTimeTextField = new JTextField();
		minServiceTimeTextField.setPreferredSize(new Dimension(200, 25));
		maxServiceTimeLabel = new JLabel("Maximum service time:");
		maxServiceTimeTextField = new JTextField();
		maxServiceTimeTextField.setPreferredSize(new Dimension(200, 25));
		
		panel3 = new JPanel();
		panel3.add(minServiceTimeLabel);
		panel3.add(minServiceTimeTextField);
		panel3.add(maxServiceTimeLabel);
		panel3.add(maxServiceTimeTextField);
		
		startSimulationButton = new JButton("Start simulation");
		startSimulationButton.addActionListener(new simulationButtonActionListener());
		
		simulationTimeLabel = new JLabel("Simulation time:");
		simulationTimeTextField = new JTextField();
		simulationTimeTextField.setPreferredSize(new Dimension(200,25));
		
		panel4 = new JPanel();
		panel4.add(simulationTimeLabel);
		panel4.add(simulationTimeTextField);
		
		panel5 = new JPanel();
		panel5.add(startSimulationButton);
		
		
		
		queue1Label = new JLabel("Queue #0");
		queue2Label = new JLabel("Queue #1");
		queue3Label = new JLabel("Queue #2");
		queue4Label = new JLabel("Queue #3");
		queue5Label = new JLabel("Queue #4");
		
		queue1TextField = new JTextField();
		queue1TextField.setPreferredSize(new Dimension(200, 25));
		queue2TextField = new JTextField();
		queue2TextField.setPreferredSize(new Dimension(200, 25));
		queue3TextField = new JTextField();
		queue3TextField.setPreferredSize(new Dimension(200, 25));
		queue4TextField = new JTextField();
		queue4TextField.setPreferredSize(new Dimension(200, 25));
		queue5TextField = new JTextField();
		queue5TextField.setPreferredSize(new Dimension(200, 25));
		queue1TextField.setEditable(false);
		queue2TextField.setEditable(false);
		queue3TextField.setEditable(false);
		queue4TextField.setEditable(false);
		queue5TextField.setEditable(false);
		
		queue1Panel = new JPanel();
		queue2Panel = new JPanel();
		queue3Panel = new JPanel();
		queue4Panel = new JPanel();
		queue5Panel = new JPanel();
		
		queue1Panel.add(queue1Label);
		queue1Panel.add(queue1TextField);
		queue2Panel.add(queue2Label);
		queue2Panel.add(queue2TextField);
		queue3Panel.add(queue3Label);
		queue3Panel.add(queue3TextField);
		queue4Panel.add(queue4Label);
		queue4Panel.add(queue4TextField);
		queue5Panel.add(queue5Label);
		queue5Panel.add(queue5TextField);
		
		logPanel = new JPanel();
		logTextArea = new JTextArea(52,30);
		scroll = new JScrollPane(logTextArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		
		visualQueuePanel = new JPanel();
		finalPanel = new JPanel();
		inputPanel = new JPanel();
		logPanel.add(scroll);
		
		visualQueuePanel.add(queue1Panel);
		visualQueuePanel.add(queue2Panel);
		visualQueuePanel.add(queue3Panel);
		visualQueuePanel.add(queue4Panel);
		visualQueuePanel.add(queue5Panel);
		
		
		inputPanel.add(panel1);
		inputPanel.add(panel2);
		inputPanel.add(panel3);
		inputPanel.add(panel4);
		inputPanel.add(panel5);
		
		leftwardPanel = new JPanel();
		leftwardPanel.add(inputPanel);
		leftwardPanel.add(visualQueuePanel);
		
		finalPanel.add(leftwardPanel);
		finalPanel.add(logPanel);
		
		visualQueuePanel.setLayout(new BoxLayout(visualQueuePanel, BoxLayout.Y_AXIS));
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		leftwardPanel.setLayout(new BoxLayout(leftwardPanel, BoxLayout.Y_AXIS));
		finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.X_AXIS));
		
		jframe.add(finalPanel);
		jframe.setResizable(false);
		jframe.setContentPane(finalPanel);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		GUI gui = new GUI();
	}
	

	public static void updateQueueTextField(Queue q)
	{
		int nbOfCustomers = q.getCustomersNumber();
		String s = "";
		for(int i = 0; i < nbOfCustomers; i++)
		{
			s += "#";
		}
		if(q.getID() == 0)
		{
			queue1TextField.setText(s);
		}
		else 
		if(q.getID() == 1)
		{
			queue2TextField.setText(s);
		}
		else 
		if(q.getID() == 2)
		{
			queue3TextField.setText(s);
		}
		else 
		if(q.getID() == 3)
		{
			queue4TextField.setText(s);
		}
		else 
		if(q.getID() == 4)
		{
			queue5TextField.setText(s);
		}
		
	}
	
	public static void updateLogTextArea(String s)
	{
		logTextArea.setText(logTextArea.getText() + s);
	}
	
	private class simulationButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
				Controller controller = new Controller(Integer.parseInt(numberOfQueuesTextField.getText()));
				controller.setMinIntervalOfClientsArrival(Integer.parseInt(minIntervalOfClientsArrivalTextField.getText())); 
				controller.setMaxIntervalOfClientsArrival(Integer.parseInt(maxIntervalOfClientsArrivalTextField.getText()));
				controller.setMinServiceTime(Integer.parseInt(minServiceTimeTextField.getText()));
				controller.setMaxServiceTime(Integer.parseInt(maxServiceTimeTextField.getText()));
				controller.setSimulationTime(Integer.parseInt(simulationTimeTextField.getText()));
				controller.start();
		}
	}
	
}
