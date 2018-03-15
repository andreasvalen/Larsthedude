
public class Message {

	private String text = "";
	private String name = "";
	private Integer group = 1;
	
	public Message(String name, String msg) {
		this.text = msg;
		this.name = name;
	}
	

	public String getName(){
		return name;
	}
	public String getText(){
		return text;
	}
}
