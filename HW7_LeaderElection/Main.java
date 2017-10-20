package HW7_LeaderElection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	Processor p1, p2, p3, p4, p5;
	int noOfProcessors;
	
	public Main() {
		init();
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		m.displayRing();
	
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new Runnable() {
			public void run() {
				m.p1.send(m.p1.id);
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				m.p2.send(m.p2.id);
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				m.p3.send(m.p3.id);
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				m.p4.send(m.p4.id);
			}
		});
		executor.execute(new Runnable() {
			public void run() {
				m.p5.send(m.p5.id);
			}
		});
		
		executor.shutdown();
		
	}
	
	public void init() {
		noOfProcessors = 5;
		
		p1 = new Processor(1);
		p2 = new Processor(2);
		p3 = new Processor(3);
		p4 = new Processor(4);
		p5 = new Processor(5);
		
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
	
	public void displayRing() {
		System.out.println("The initial ring structure is:");
		System.out.println("Processor" +p1.left.id+ " <---------- Processor" +p1.id+ " ---------> Processor" +p1.right.id);
		System.out.println("Processor" +p2.left.id+ " <---------- Processor" +p2.id+ " ---------> Processor" +p2.right.id);
		System.out.println("Processor" +p3.left.id+ " <---------- Processor" +p3.id+ " ---------> Processor" +p3.right.id);
		System.out.println("Processor" +p4.left.id+ " <---------- Processor" +p4.id+ " ---------> Processor" +p4.right.id);
		System.out.println("Processor" +p5.left.id+ " <---------- Processor" +p5.id+ " ---------> Processor" +p5.right.id);
		System.out.println();
	}
	
}

