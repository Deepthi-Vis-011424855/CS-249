package HW7_LeaderElection_Onlogn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	Processor p1, p2, p3, p4, p5;
	int noOfProcessors;
	List<Processor> listOfProcessors;

	public Main() {
		init();
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.displayRing();
		
		/*
		 * An executor service is used to spawn one thread for each processor.
		 * This calls the send and receive methods in Processor.java
		 */
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new Runnable() {
			public void run() {
				Message msg = new Message(MessageType.PROBE,m.p1.id,0,1);
				msg.setSender(m.p1);
				System.out.println("=========================== PHASE " + msg.phase+ " ===========================");
				m.p1.send(msg);
				while (!executor.isShutdown()) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m.p1.recieve();
				}
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				Message msg = new Message(MessageType.PROBE,m.p2.id,0,1);
				msg.setSender(m.p2);
				m.p2.send(msg);
				while (!executor.isShutdown()) {
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m.p2.recieve();
				}
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				Message msg = new Message(MessageType.PROBE,m.p3.id,0,1);
				msg.setSender(m.p3);
				m.p3.send(msg);
				while (!executor.isShutdown()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m.p3.recieve();
				}
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				Message msg = new Message(MessageType.PROBE,m.p4.id,0,1);
				msg.setSender(m.p4);
				m.p4.send(msg);
				while (!executor.isShutdown()) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m.p4.recieve();
				}
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				Message msg = new Message(MessageType.PROBE,m.p5.id,0,1);
				msg.setSender(m.p5);
				m.p5.send(msg);
				while (!executor.isShutdown()) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m.p5.recieve();
				}
			}
		});
	}
	/*
	 * Initializes the ring structure
	 */
	public void init() {
		noOfProcessors = 5;

		p1 = new Processor(50);
		p2 = new Processor(10);
		p3 = new Processor(30);
		p4 = new Processor(5);
		p5 = new Processor(1);

		listOfProcessors = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5));

		p1.left = p4;
		p1.right = p3;

		p2.left = p5;
		p2.right = p4;

		p3.left = p1;
		p3.right = p5;

		p4.left = p2;
		p4.right = p1;

		p5.left = p3;
		p5.right = p2;
	}
	/*
	 * A method to display the initial ring structure
	 */
	public void displayRing() {
		System.out.println("The initial ring structure is:");
		for (int i = 0; i < listOfProcessors.size(); i++) {
			System.out.println("Processor" + listOfProcessors.get(i).left.id + " <---------- Processor"
					+ listOfProcessors.get(i).id + " ---------> Processor" + listOfProcessors.get(i).right.id);
		}
		System.out.println();
	}
}

