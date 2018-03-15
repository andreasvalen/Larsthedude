import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{

	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket socket;
	private  String name = "";
	
	public Client(String host, String navnet) {
		super(navnet);
		name = navnet;
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener() {					
					public void actionPerformed(ActionEvent event) {
						sendMessage(event.getActionCommand());
						userText.setText("");					
					}
				}
			);
			add(userText, BorderLayout.NORTH);
			chatWindow = new JTextArea();
			add(new JScrollPane(chatWindow), BorderLayout.CENTER);
			setSize(300, 150);
			setVisible(true);
	}
	
	public void startRunning() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException eofe) {
			showMessage("\n Client terminated connection");			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			closeCrap();
		}
	}
	
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		socket = new Socket(InetAddress.getByName(serverIP), 3333);
		showMessage("Connected to: "+socket.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(socket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(socket.getInputStream());
		showMessage("\n Streams connected and goooooooodee magud nega! \n");		
	}
	
	private void whileChatting() throws IOException {
		ableToType(true);
		String end = "";
		do {
			
			try {
				Message msgIn;
				msgIn = (Message) input.readObject();
				
				end = msgIn.getText();  
				showMessage(end);
			}catch(ClassNotFoundException cnfe) {
				showMessage("\n I don't know that object type");
			}			
		}while(!end.equals("END"));
	}
	
	private void closeCrap() {
		showMessage("\n closing crap down.. ");
		ableToType(false);
		try {
			output.close();
			input.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String message) {
		try {
			
			Message msg;
			msg = new Message(name, message);
			output.writeObject(msg);
			output.flush();
			showMessage("\n" + msg.getName() + " - " + msg.getText());
		}catch(IOException e) {
			chatWindow.append("\n something messed up sending message big big boi");
		}
	}
	
	private void showMessage(final String text) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						chatWindow.append(text);
					}
				}
			);
	}
	
	private void ableToType(final boolean bool) {
		SwingUtilities.invokeLater(
			new Runnable() {
				public void run() {
					userText.setEditable(bool);	
					
					}
				}
			);
		
	}
	
	
			
			
	
		
	
}












