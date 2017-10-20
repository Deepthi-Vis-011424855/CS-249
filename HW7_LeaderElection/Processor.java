package HW7_LeaderElection;

public class Processor {
	int id;
	Processor left;
	Processor right;
	
	Processor(int id) {
		this.id = id;
		this.left = null;
		this.right = null;
	}
	
	public void send(int msg) {
		System.out.println("Sending message <<"+msg+ ">> from Processor" +this.id+ " to Processor"+this.left.id);
		this.left.recieve(msg);
	}
	
	public void recieve(int msg) {
		if (msg > this.id) {
			System.out.println("The message received is <<" +msg+">> at Processor" +this.id+" from Processor"+this.right.id);
			System.out.println();
			System.out.println("******The message <<" +msg+ ">> is greater than the ID of the current processor's ID:" +this.id+ "******");
			System.out.println("Forwarding the message <<" +msg+ ">> from current processor Processor" +this.id+ " to the processor at left Processor" +this.left.id);
			System.out.println("--------------------------------------------------------------");
			this.send(msg);
		} else if (msg == this.id) {
			System.out.println("Current processor:" +this.id+ " Message received: " +msg);
			System.out.println("!!!!!! Received own ID - Terminating as leader !!!!!!");
			System.out.println();
			System.out.println("The leader process is Processor"+this.id);
		} else {
			System.out.println();
			System.out.println("^^^^^^ Swallowing the message <<" +msg+ ">> at Processor" +this.id+ " which is received from Processor" +this.right.id+ "^^^^^^");
		}
	}
}