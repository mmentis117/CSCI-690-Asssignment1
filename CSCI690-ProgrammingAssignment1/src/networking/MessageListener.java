package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageListener extends Thread{
	
	ServerSocket server;
	int port = 8877;
	WritableGUI gui;
	public MessageListener(WritableGUI gui, int port){
		this.port = port;
		this.gui = gui;
		try
		{
			server = new ServerSocket(port);
		}
		catch(IOException e){
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		
	}
	
	public MessageListener(){
		try
		{
			server = new ServerSocket(port);
		}
		catch(IOException e){
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		Socket clientSocket;
		try {
			while((clientSocket = server.accept()) != null)
			{
				InputStream inputStream = clientSocket.getInputStream();
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
				String message = bufferReader.readLine();
				if(message != null)
				{
					gui.write(message);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
