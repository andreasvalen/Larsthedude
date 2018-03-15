import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientTest {


	public static void main(String[] args) {
		Client scoobster;
		scoobster = new Client("127.0.0.1", setName());
		scoobster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoobster.startRunning();
	}
	
	public static String setName() {
		String s = "";
		s = JOptionPane.showInputDialog("Write your nick:" );
		return s;
	}
}
