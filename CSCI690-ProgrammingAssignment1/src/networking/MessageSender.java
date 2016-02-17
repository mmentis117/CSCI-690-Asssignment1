package networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import gui.ChatGUI;

public class MessageSender extends Thread{
	
	InetAddress ipAdd;
	String message, ipAddress;
	int port;
	ChatGUI gui;
	
	public MessageSender(ChatGUI gui, String message, String ipAddress, int port)
	{
		this.message = message;
		this.ipAddress = ipAddress;
		this.port = port;
		this.gui = gui;
	}


@Override
public void run() {
	try{
		//get IP Address by name (Can be string of IP Address)
		ipAdd = InetAddress.getByName(ipAddress);
		
		//create Socket with current IP Address and Port number inputed by user
		Socket currentSocket = new Socket(ipAdd, port);
		
		//write message to the current output stream
		currentSocket.getOutputStream().write(message.getBytes());
		
		//close socket
		currentSocket.close();
	}
	catch(IOException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
		gui.writeChatText("Message failed to be sent to:\n\tIP Address : " + ipAdd.toString() + "\n\tPort : " + port);
	}	
	}
}
