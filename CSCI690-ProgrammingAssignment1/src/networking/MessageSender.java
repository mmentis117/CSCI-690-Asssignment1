package networking;

import java.io.IOException;
import java.net.Socket;

public class MessageSender extends Thread{
	
	String message, ipAddress;
	int port;
	
	public MessageSender(String message, String ipAddress, int port)
	{
		this.message = message;
		this.ipAddress = ipAddress;
		this.port = port;
	}


@Override
public void run() {
	try{
		Socket currentSocket = new Socket(ipAddress, port);
		currentSocket.getOutputStream().write(message.getBytes());
		currentSocket.close();
	}
	catch(IOException e){
		// TODO Auto-generated catch block
					e.printStackTrace();
	}	
	}
}
