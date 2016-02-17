package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import networking.MessageListener;
import networking.MessageSender;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.Window.Type;
import java.awt.Font;

public class ChatGUI{

	private JFrame frmChatApplication;
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
					window.frmChatApplication.setVisible(true);
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
		frmChatApplication = new JFrame();
		frmChatApplication.setTitle("Chat Application");
		frmChatApplication.getContentPane().setBackground(Color.BLACK);
		frmChatApplication.setBounds(0, -48, 761, 413);
		frmChatApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatApplication.getContentPane().setLayout(null);
		
		ipAddressTextField = new JTextField();
		ipAddressTextField.setFont(new Font("Bodoni MT Black", Font.PLAIN, 16));
		ipAddressTextField.setBackground(Color.LIGHT_GRAY);
		ipAddressTextField.setText("localhost");
		ipAddressTextField.setBounds(261, 12, 161, 20);
		frmChatApplication.getContentPane().add(ipAddressTextField);
		ipAddressTextField.setColumns(10);
		
		targetPort = new JTextField();
		targetPort.setFont(new Font("Bodoni MT Black", Font.PLAIN, 16));
		targetPort.setBackground(Color.LIGHT_GRAY);
		targetPort.setText("8802");
		targetPort.setBounds(540, 12, 86, 20);
		frmChatApplication.getContentPane().add(targetPort);
		targetPort.setColumns(10);
		
		chatText = new JTextArea();
		chatText.setFont(new Font("Bodoni MT Black", Font.PLAIN, 16));
		chatText.setEditable(false);
		chatText.setLineWrap(true);
		chatText.setWrapStyleWord(true);
		chatText.setBackground(Color.LIGHT_GRAY);
		chatText.setBounds(163, 9, 694, 287);
		frmChatApplication.getContentPane().add(chatText);
		
		JScrollPane scrollPane = new JScrollPane(chatText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 43, 715, 287);
		frmChatApplication.getContentPane().add(scrollPane);
		
		message = new JTextField();
		message.setFont(new Font("Bodoni MT Black", Font.PLAIN, 16));
		message.setBackground(Color.LIGHT_GRAY);
		message.setBounds(86, 341, 371, 20);
		frmChatApplication.getContentPane().add(message);
		message.setColumns(10);
		
		JButton enterButton = new JButton("Enter");
		enterButton.setForeground(Color.WHITE);
		enterButton.setBackground(Color.DARK_GRAY);
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();

			}
		});
		enterButton.setBounds(495, 340, 89, 23);
		frmChatApplication.getContentPane().add(enterButton);
		
		JButton Start = new JButton("Start");
		Start.setForeground(Color.WHITE);
		Start.setBackground(Color.DARK_GRAY);
		Start.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				startMessageListener();
			}
		});
		Start.setBounds(636, 11, 89, 23);
		frmChatApplication.getContentPane().add(Start);
		
		receivingPort = new JTextField();
		receivingPort.setFont(new Font("Bodoni MT Black", Font.PLAIN, 16));
		receivingPort.setBackground(Color.LIGHT_GRAY);
		receivingPort.setText("8801");
		receivingPort.setBounds(80, 12, 86, 20);
		frmChatApplication.getContentPane().add(receivingPort);
		receivingPort.setColumns(10);
		
		JLabel lblYourPort = new JLabel("Your port :");
		lblYourPort.setForeground(Color.WHITE);
		lblYourPort.setBounds(10, 15, 70, 14);
		frmChatApplication.getContentPane().add(lblYourPort);
		
		JLabel lblTargetIp = new JLabel("Target IP :");
		lblTargetIp.setForeground(Color.WHITE);
		lblTargetIp.setBounds(180, 15, 79, 14);
		frmChatApplication.getContentPane().add(lblTargetIp);
		
		JLabel lblTargetPort = new JLabel("Target port :");
		lblTargetPort.setForeground(Color.WHITE);
		lblTargetPort.setBounds(453, 15, 86, 14);
		frmChatApplication.getContentPane().add(lblTargetPort);
		
		JLabel lblMessage = new JLabel("Message :");
		lblMessage.setForeground(Color.WHITE);
		lblMessage.setBounds(20, 344, 79, 14);
		frmChatApplication.getContentPane().add(lblMessage);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setForeground(Color.WHITE);
		btnQuit.setBackground(Color.DARK_GRAY);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setBounds(615, 340, 89, 23);
		frmChatApplication.getContentPane().add(btnQuit);
	}
	
	//Start listening for messages on start button press
	public void startMessageListener() {
		listener = new MessageListener(this, Integer.parseInt(receivingPort.getText()));
		listener.start();
	}
	
	//Send message to target user on enter button press
	public void sendMessage() {
		
		//Send user message to target IP and port
		MessageSender sender = new MessageSender(this, message.getText(), ipAddressTextField.getText(), Integer.parseInt(targetPort.getText()));
		sender.start();			
		
		if(!message.getText().equals(""))
		{
			//print message to gui if not blank
			chatText.append(("Me : "+ message.getText() + "\n"));
			DefaultCaret caret = (DefaultCaret)chatText.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			message.setText(null);
		}
	}

	//Write the current Friend message to the gui chat text frame
	public void writeFriendsChatText(String message) {
		chatText.append("Friend : "+ message + "\n");
		DefaultCaret caret = (DefaultCaret)chatText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	//Write the current message to the gui chat text frame
	public void writeChatText(String message) {
		chatText.append(message + "\n");
		DefaultCaret caret = (DefaultCaret)chatText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
}
