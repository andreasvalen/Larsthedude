import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerThread extends Thread {
	
	Socket socket;
	DifferentServer server;
	
	ObjectInputStream in;
	ObjectOutputStream out;
	boolean shouldRun = true;
	
	
	public ServerThread(Socket socket, DifferentServer server){
			super("ServerThread");
			this.socket = socket;		
			this.server = server;
	}
	
	public void sendObjectToClient(Message msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendObjectToAllClients(Message msg) {
		for(int index = 0; index < server.connections.size(); index++ ) {
			ServerThread sThread = server.connections.get(index);
			sThread.sendObjectToClient(msg);
		}
	}
	
	public void run() {
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			while(shouldRun) {
				while(in.available()==0) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			
			try {
				Message msgIn = (Message) in.readObject();
				sendObjectToAllClients(msgIn);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}






