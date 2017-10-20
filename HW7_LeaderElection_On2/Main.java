package HW7_LeaderElection_On2;

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
				m.p1.send(m.p1.id);
				while (!executor.isShutdown()) {
					m.p1.recieve();
				}
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				m.p2.send(m.p2.id);
				while (!executor.isShutdown()) {
					m.p2.recieve();
				}
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				m.p3.send(m.p3.id);
				while (!executor.isShutdown()) {
					m.p3.recieve();
				}
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				m.p4.send(m.p4.id);
				while (!executor.isShutdown()) {
					m.p4.recieve();
				}
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				m.p5.send(m.p5.id);
				while (!executor.isShutdown()) {
					m.p5.recieve();
				}
			}
		});

		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();

		m.printLeader();

	}
	/*
	 * Initializes the ring structure
	 */
	public void init() {
		noOfProcessors = 5;

		p1 = new Processor(1);
		p2 = new Processor(2);
		p3 = new Processor(3);
		p4 = new Processor(4);
		p5 = new Processor(5);

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
	/*
	 *  A method to print the finally selected leader.
	 */
	public void printLeader() {
		System.out.println();
		for (int i = 0; i < listOfProcessors.size(); i++) {
			if (listOfProcessors.get(i).isLeader) {
				System.out.println("--------------------------------------------------------------");
				System.out.println("Processor P" + listOfProcessors.get(i).id + " is the LEADER.");
			}
		}

	}

}
