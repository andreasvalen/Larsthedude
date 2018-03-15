import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DifferentServer {

	ServerSocket ss;
	ArrayList<ServerThread> connections = new ArrayList<ServerThread>();
	boolean shouldRun = true;

	public static void main(String[] args) {
		new DifferentServer();
	}
	
	public DifferentServer() {
		try {
			ss = new ServerSocket(3333);
			while(shouldRun) {
			
				Socket s = ss.accept();
				ServerThread sThread = new ServerThread(s, this);
				sThread.start();
				connections.add(sThread);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
