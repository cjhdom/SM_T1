import java.io.IOException;


public class Child extends User{
	public Message msg;
	private Database db;
	public Child(Database db){
		msg = new Message();
		this.db = db;
	}
	public Message receiveMsg(){
		msg = db.setMsg();
		if(msg==null)return null;
		if(msg.getQuestion()==null) return null;
		else{
			return msg;
		}
	}
	public String checkMsg(String buffer){
		if(msg.getAnswer().equalsIgnoreCase(buffer)){
			if(msg.getResound()!=null){
				setSoundPath(msg.getResound());
				displaySound();
			}
			return msg.getReRight();
		}
		else {
			return msg.getReWrong();
		}
	}
}
