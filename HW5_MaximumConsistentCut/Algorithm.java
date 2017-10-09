package HW5_MaximumConsistentCut;

public class Algorithm {
	int noOfProcessors;
	Processor p0, p1; // Assume there are two processors.

	public Algorithm() {
		super();
		this.noOfProcessors = 2;
		p0 = new Processor(0, 2);
		p1 = new Processor(1, 2);
		
	}

	public void executionPlanForP0() {
	  send(p0, new Message(MessageType.SEND, p1.vc)); //To update vc of P0 before a SEND message
	  send(p1, new Message(MessageType.RECIEVE, p0.vc));
    compute(p0, new Message(MessageType.COMPUTATION, p0.vc));
    compute(p0, new Message(MessageType.COMPUTATION, p0.vc));
    send(p0, new Message(MessageType.SEND, p1.vc));
    
	}

	public void executionPlanForP1() {
	  
	  compute(p1, new Message(MessageType.COMPUTATION, p1.vc));
    compute(p1, new Message(MessageType.COMPUTATION, p1.vc));
    compute(p1, new Message(MessageType.COMPUTATION, p1.vc));
    compute(p1, new Message(MessageType.COMPUTATION, p1.vc));
    send(p1, new Message(MessageType.RECIEVE, p0.vc));
	}

	
	/**
	 * Function to print the store array of P0 and P1
	 */
	public void printStoreArrays() {
	  p0.printStoreArray();
    p1.printStoreArray();
	}
	
	public void compute(Processor p, Message computeMessage) {
		p.sendMessageToMyBuffer(computeMessage);
	}

	public void send(Processor to, Message m) {
		to.sendMessageToMyBuffer(m);
	}

	/**
	 * A function which calls calculateMaxCut for 
	 * the input cut and prints the final max consistent cut.
	 * @param cut
	 */
	public void printMaxCut(int cut[]) {
	 int c1= p0.calculateMaxCut(cut);
	 int c2= p1.calculateMaxCut(cut);
	 
	 System.out.println();
	 System.out.println("Max cut is: {"+ c1 + ", " + c2 + "}");
	}
}
