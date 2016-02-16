package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import networking.MessageListener;
import networking.MessageSender;
import networking.WritableGUI;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ChatGUI implements WritableGUI{

	private JFrame frame;
	private JTextField ipAddressTextField;
	private JTextField targetPort;
	private JTextField message;
	private JTextField receivingPort;
	private JTextArea chatText;
	MessageListener listener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGUI window = new ChatGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, -48, 761, 413);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		ipAddressTextField = new JTextField();
		ipAddressTextField.setText("localhost");
		ipAddressTextField.setBounds(268, 12, 184, 20);
		frame.getContentPane().add(ipAddressTextField);
		ipAddressTextField.setColumns(10);
		
		targetPort = new JTextField();
		targetPort.setText("8877");
		targetPort.setBounds(540, 12, 86, 20);
		frame.getContentPane().add(targetPort);
		targetPort.setColumns(10);
		
		chatText = new JTextArea();
		chatText.setBounds(10, 43, 715, 287);
		frame.getContentPane().add(chatText);
		
		message = new JTextField();
		message.setBounds(86, 341, 371, 20);
		frame.getContentPane().add(message);
		message.setColumns(10);
		
		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageSender transmitter = new MessageSender(message.getText(), ipAddressTextField.getText(), Integer.parseInt(targetPort.getText()));
				transmitter.start();			
				
				chatText.append(("Me : "+ message.getText() + System.lineSeparator()));
				message.setText(null);
			}
		});
		enterButton.setBounds(495, 340, 89, 23);
		frame.getContentPane().add(enterButton);
		
		JButton Start = new JButton("Start");
		Start.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				startAction();
			}
		});
		Start.setBounds(636, 11, 89, 23);
		frame.getContentPane().add(Start);
		
		receivingPort = new JTextField();
		receivingPort.setText("8878");
		receivingPort.setBounds(77, 12, 114, 20);
		frame.getContentPane().add(receivingPort);
		receivingPort.setColumns(10);
		
		JLabel lblYourPort = new JLabel("Your port :");
		lblYourPort.setBounds(10, 15, 59, 14);
		frame.getContentPane().add(lblYourPort);
		
		JLabel lblTargetIp = new JLabel("Target IP :");
		lblTargetIp.setBounds(202, 15, 59, 14);
		frame.getContentPane().add(lblTargetIp);
		
		JLabel lblTargetPort = new JLabel("Target port :");
		lblTargetPort.setBounds(460, 15, 70, 14);
		frame.getContentPane().add(lblTargetPort);
		
		JLabel lblMessage = new JLabel("Message :");
		lblMessage.setBounds(20, 344, 79, 14);
		frame.getContentPane().add(lblMessage);
	}
	
	public void startAction() {
		listener = new MessageListener(this, Integer.parseInt(receivingPort.getText()));
		listener.start();
	}

	@Override
	public void write(String message) {
		// TODO Auto-generated method stub
		chatText.append("Friend : "+ message + System.lineSeparator());
	}
}
