package HW7_LeaderElection_On2;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class Processor {
	int id;
	Processor left;
	Processor right;
	LinkedBlockingQueue queue;
	Executor exec;
	boolean isLeader;

	Processor(int id) {
		this.id = id;
		this.left = null;
		this.right = null;
		isLeader = false;
		queue = new LinkedBlockingQueue<>();
	}

	public void send(int msg) {
		System.out.println("Sending message <<" + msg + ">> from Processor" + this.id + " to Processor" + this.left.id);
		this.left.queue.add(msg);
	}

	public void recieve() {
		int otherProcessorId;
		if (!this.queue.isEmpty()) {
			otherProcessorId = (int) this.queue.poll();
			if (!this.isLeader) {
				System.out.println("Processor " + this.id + " received message <<" +otherProcessorId+ ">>");
			}
			if (otherProcessorId == -1 && !(this.isLeader)) {
				System.out.println();
				System.out.println("Processor P" + this.id + " TERMINATED as a non-leader.");
				System.out.println();
				send(-1);
			} else if (otherProcessorId < this.id) {
				if (!this.isLeader) {
					System.out.println();
					System.out.println("Processor " + this.id + " swallowed the message <<" + otherProcessorId
							+ ">> from Processor" + otherProcessorId);
				}
			} else if (otherProcessorId == this.id) {
				System.out.println();
				System.out.println("RECEIVED MY OWN ID AGAIN");
				System.out.println("***Sending <<-1>> as termination message***");
				this.isLeader = true;
				send(-1);
			} else {
				System.out.println();
				System.out.println("Processor " + this.id + " forwarding message <<" + otherProcessorId + ">> from "
						+ otherProcessorId);
				send(otherProcessorId);
			}
		}
	}
}