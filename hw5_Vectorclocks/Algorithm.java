package hw5_Vectorclocks;

import java.util.List;

public class Algorithm {
	int noOfProcessors;
	Processor p0, p1, p2; // Assume there are three processors.

	public Algorithm() {
		super();
		this.noOfProcessors = 3;
		// TODO : initialize all the processors
		p0 = new Processor(0, 3);
		p1 = new Processor(1, 3);
		p2 = new Processor(2, 3);
	}

	// Write hard coded execution plan for processors
	public void executionPlanForP0() {
		// TODO: call events on P0 for compute.
		// Call send events to send message
		// Call receive messages
		send(p0, new Message(MessageType.SEND, p0.vc));
		send(p1, new Message(MessageType.RECIEVE, p0.vc));
		send(p0, new Message(MessageType.SEND, p0.vc));
		send(p2, new Message(MessageType.RECIEVE, p0.vc));
		compute(p0, new Message(MessageType.COMPUTATION, p0.vc));
		//send(p0, new Message(MessageType.RECIEVE, p2.vc));
		compute(p0, new Message(MessageType.COMPUTATION, p0.vc));
	}

	// Write hard coded execution plan for processors
	public void executionPlanForP1() {
		// TODO: call events on P0 for compute.
		// Call send events to send message
		// Call receive messages
		//send(p1, new Message(MessageType.RECIEVE, p0.vc));
		//send(p1, new Message(MessageType.RECIEVE, p2.vc));
		send(p1, new Message(MessageType.SEND, p1.vc));
		send(p2, new Message(MessageType.RECIEVE, p1.vc));
		send(p1, new Message(MessageType.SEND, p1.vc));
		send(p0, new Message(MessageType.RECIEVE, p1.vc));
	}

	// Write hard coded execution plan for processors
	public void executionPlanForP2() {
		// TODO: call events on P0 for compute.
		// Call send events to send message
		// Call receive messages
		compute(p2, new Message(MessageType.COMPUTATION, p2.vc));
		compute(p2, new Message(MessageType.COMPUTATION, p2.vc));
		send(p2, new Message(MessageType.SEND, p2.vc));
		send(p1, new Message(MessageType.RECIEVE, p2.vc));
		
		send(p2, new Message(MessageType.SEND, p2.vc));
		send(p1, new Message(MessageType.RECIEVE, p2.vc));
		
		//send(p2, new Message(MessageType.RECIEVE, p0.vc));
		//send(p2, new Message(MessageType.RECIEVE, p0.vc));
		//send(p2, new Message(MessageType.RECIEVE, p1.vc));
		compute(p2, new Message(MessageType.COMPUTATION, p2.vc));
		
		System.out.println("The final vector clocks are:");
		System.out.print("P0: ");
		for (int i=0; i<noOfProcessors; i++) {
			System.out.print(p0.vc.vc[i]);
		}
		System.out.println();
		System.out.print("P1: ");
		for (int i=0; i<noOfProcessors; i++) {
			System.out.print(p1.vc.vc[i]);
		}
		System.out.println();
		System.out.print("P2: ");
		for (int i=0; i<noOfProcessors; i++) {
			System.out.print(p2.vc.vc[i]);
		}
	}
	/**
	 * 
	 * @param p
	 * @param computeMessage
	 */
	public void compute(Processor p, Message computeMessage) {
		// TODO: implement. What will be the value of vector clock passed to
		// this message?
		p.sendMessageToMyBuffer(computeMessage);
	}

	public void send(Processor to, Message m) {
		// TODO: implement. What will be the value of vector clock passed to
		// this message?
		to.sendMessageToMyBuffer(m);
	}

}
