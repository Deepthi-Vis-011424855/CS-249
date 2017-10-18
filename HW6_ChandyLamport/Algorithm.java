package HW6_ChandyLamport;

/**
 * This is the simulation of a main BONJOUR that will run on processors P1, P2,
 * P3 This could be a banking application, payroll application or any other
 * distributed application
 */
public class Algorithm extends Thread {

	/**
	 * The processors which will participate in a distributed application
	 */
	Processor processor1, processor2, processor3;
	int threadNo;

	public Algorithm(Processor processor1, Processor processor2, Processor processor3, int threadNo) {
		this.processor1 = processor1;
		this.processor2 = processor2;
		this.processor3 = processor3;
		this.threadNo = threadNo;
		// TODO: Homeork: Initialize processors so that they represent the topology of 3
		// processor system
	}

	public void executionPlanP1() {
		try {
			compute(processor1);
			Thread.sleep(2000);
			processor1.sendMessgeTo(new Message(MessageType.HELLO), processor1.outChannels.get(0)); // C13
			Thread.sleep(2000);
			compute(processor1);
			Thread.sleep(2000);
			processor1.sendMessgeTo(new Message(MessageType.HELLO), processor1.outChannels.get(1)); // C12
			Thread.sleep(2000);
			compute(processor1);
			Thread.sleep(2000);
			processor1.sendMessgeTo(new Message(MessageType.HELLO), processor1.outChannels.get(0)); // C13
			Thread.sleep(2000);
			compute(processor1);
			Thread.sleep(2000);
			processor1.sendMessgeTo(new Message(MessageType.BONJOUR), processor1.outChannels.get(1)); // C12
			Thread.sleep(2000);
			compute(processor1);
			Thread.sleep(2000);
			processor1.sendMessgeTo(new Message(MessageType.BONJOUR), processor1.outChannels.get(0)); // C13
			Thread.sleep(2000);
			compute(processor1);
			Thread.sleep(2000);
			processor1.sendMessgeTo(new Message(MessageType.CIAO), processor1.outChannels.get(1)); // C12
			Thread.sleep(2000);
			compute(processor1);
			Thread.sleep(2000);
			processor1.sendMessgeTo(new Message(MessageType.BONJOUR), processor1.outChannels.get(0)); // C13
			Thread.sleep(2000);
			compute(processor1);
			Thread.sleep(2000);
			processor1.sendMessgeTo(new Message(MessageType.HELLO), processor1.outChannels.get(1)); // C12
			Thread.sleep(2000);
			compute(processor1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * TODO: Homework: Implement send message from processor1 to different
		 * processors. Add a time gap betweeen two different send events. Add
		 * computation events between two diferent sends. [Hint: Create a loop that
		 * kills time, sleep , wait on somevalue etc..]
		 *
		 */

	}

	// Write hard coded execution plan for processors
	public void executionPlanP2() {
		try {
			compute(processor2);
			Thread.sleep(1500);
			processor2.sendMessgeTo(new Message(MessageType.BONJOUR), processor2.outChannels.get(0)); // C21
			Thread.sleep(1500);
			compute(processor2);
			Thread.sleep(1500);
			processor2.sendMessgeTo(new Message(MessageType.BONJOUR), processor2.outChannels.get(1)); // C23
			Thread.sleep(1500);
			compute(processor2);
			Thread.sleep(1500);
			processor2.sendMessgeTo(new Message(MessageType.HELLO), processor2.outChannels.get(0)); // C21
			Thread.sleep(1500);
			compute(processor2);
			Thread.sleep(1500);
			processor2.sendMessgeTo(new Message(MessageType.HELLO), processor2.outChannels.get(1)); // C23
			Thread.sleep(1500);
			compute(processor2);
			Thread.sleep(1500);
			processor2.sendMessgeTo(new Message(MessageType.CIAO), processor2.outChannels.get(0)); // C21
			Thread.sleep(1500);
			compute(processor2);
			Thread.sleep(1500);
			processor2.sendMessgeTo(new Message(MessageType.CIAO), processor2.outChannels.get(1)); // C23
			Thread.sleep(1500);
			compute(processor2);
			Thread.sleep(1500);
			processor2.sendMessgeTo(new Message(MessageType.BONJOUR), processor2.outChannels.get(0)); // C21
			Thread.sleep(1500);
			compute(processor2);
			Thread.sleep(1500);
			processor2.sendMessgeTo(new Message(MessageType.CIAO), processor2.outChannels.get(1)); // C23
			Thread.sleep(1500);
			compute(processor2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Write hard coded execution plan for processors
	public void executionPlanP3() {
		try {
			compute(processor3);
			Thread.sleep(1000);
			processor3.sendMessgeTo(new Message(MessageType.BONJOUR), processor3.outChannels.get(0)); // C31
			Thread.sleep(1000);
			compute(processor3);
			Thread.sleep(1000);
			processor3.sendMessgeTo(new Message(MessageType.BONJOUR), processor3.outChannels.get(1)); // C32
			Thread.sleep(1000);
			compute(processor3);
			Thread.sleep(1000);
			processor3.sendMessgeTo(new Message(MessageType.BONJOUR), processor3.outChannels.get(0)); // C31
			Thread.sleep(1000);
			compute(processor3);
			Thread.sleep(1000);
			processor3.sendMessgeTo(new Message(MessageType.BONJOUR), processor3.outChannels.get(1)); // C32
			Thread.sleep(1000);
			compute(processor3);
			Thread.sleep(1000);
			processor3.sendMessgeTo(new Message(MessageType.BONJOUR), processor3.outChannels.get(0)); // C31
			Thread.sleep(1000);
			compute(processor3);
			Thread.sleep(1000);
			processor3.sendMessgeTo(new Message(MessageType.BONJOUR), processor3.outChannels.get(1)); // C32
			Thread.sleep(1000);
			compute(processor3);
			Thread.sleep(1000);
			processor3.sendMessgeTo(new Message(MessageType.BONJOUR), processor3.outChannels.get(0)); // C31
			Thread.sleep(1000);
			compute(processor3);
			Thread.sleep(1000);
			processor3.sendMessgeTo(new Message(MessageType.BONJOUR), processor3.outChannels.get(1)); // C32
			Thread.sleep(1000);
			compute(processor3);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * A dummy computation.
	 * 
	 * @param p
	 */
	public void compute(Processor p) {
		// System.out.println("Doing some computation on " +
		// p.getClass().getSimpleName());
	}

	@Override
	public void run() {
		if (this.threadNo == 1) {
			System.out.println("Starting Thread 1");
			this.executionPlanP1();
		} else if (this.threadNo == 2) {
			System.out.println("Starting Thread 2");
			this.executionPlanP2();
		} else {
			System.out.println("Starting Thread 3");
			this.executionPlanP3();
		}

	}

}