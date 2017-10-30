package HW7_LeaderElection_Onlogn;

public class Message {
	MessageType messageType;
	int id;
	int phase;
	int hop;
	
	Processor sender;
	
	public Message () {
		
	}
	
	public Message(MessageType mt, int id,int phase, int hop) {
		this.messageType = mt;
		this.id = id;
		this.phase = phase;
		this.hop = hop;
	}
	
	public Message(MessageType mt, int id,int phase) {
		this.messageType = mt;
		this.id = id;
		this.phase = phase;
	}
	
	public void setSender(Processor sender) {
		this.sender = sender;
	}
	
	public Processor getSender() {
		return this.sender;
	}
}