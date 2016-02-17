package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import gui.ChatGUI;

public class MessageListener extends Thread{
	
	
	ServerSocket server;
	int port = 8802;
	ChatGUI gui;
	public MessageListener(ChatGUI gui, int port){
		this.port = port;
		this.gui = gui;
		try
		{
			//ServerSocket : waits for requests to come in on selected port
			server = new ServerSocket(port);
			gui.writeChatText("Success! Server socket on port : " + port);
		}
		catch(IOException e){
			e.printStackTrace();
			if(e instanceof BindException)
			{
				gui.writeChatText("Failed! Port " + port + " is in use or cannot be assigned.");
			}
		}
		
	}
	
	@Override
	public void run(){
		Socket clientSocket;
		try {
			//listens for connections to server socket
			while((clientSocket = server.accept()) != null)
			{
				//get input stream from accepted socket
				InputStream inputStream = clientSocket.getInputStream();
				
				//create a character input stream buffer
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
				
				//receive message from buffer
				String message = bufferReader.readLine();
				if(message != null)
				{
					//write message to gui chat text frame
					gui.writeFriendsChatText(message);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gui.writeChatText("Failed to be recieved");
		}
	}
}
