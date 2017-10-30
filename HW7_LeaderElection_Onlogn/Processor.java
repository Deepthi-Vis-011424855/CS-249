package HW7_LeaderElection_Onlogn;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class Processor {
	int id;
	Processor left;
	Processor right;
	LinkedBlockingQueue queue;
	Executor exec;
	boolean isAsleep;
	boolean isLeader;
	boolean hasReceivedReply;

	Processor(int id) {
		this.id = id;
		this.left = null;
		this.right = null;
		queue = new LinkedBlockingQueue<Message>();
		isAsleep = true;
		hasReceivedReply = false;
	}

	public void send(Message message) {
		if (!this.isAsleep) {
			System.out.println("=========================== PHASE " + message.phase + " ===========================");
		}
		System.out.println();
		System.out.println("Sending " + message.messageType.name() + " message from Processor" + this.id
				+ " to left Processor" + this.left.id + " and right processor" + this.right.id);
		this.left.queue.add(message);
		this.right.queue.add(message);
		this.isAsleep = false;
	}

	public void send(Message message, Processor to) {
		to.queue.add(message);
	}

	public void recieve() {
		Message topMsg;
		if (!this.queue.isEmpty()) {
			topMsg = (Message) this.queue.poll();
			switch (topMsg.messageType) {
			case PROBE:

				if (topMsg.getSender() == this.left) {
					if (topMsg.id == this.id) {
						if (!this.isLeader) {
							System.out.println();
							System.out.println("------ The leader is Processor" + this.id + " ------");
							System.out.println();
							System.out.println("EXECUTION COMPLETE!!!!");
							this.isLeader = true;
						}
						System.exit(0);
					}
					if (topMsg.id > this.id && topMsg.hop < Math.pow(2, topMsg.phase)) {
						Message msg = new Message(MessageType.PROBE, topMsg.id, topMsg.phase, topMsg.hop + 1);
						msg.setSender(this);
						System.out.println();
						System.out.println("!!!!! Forwarding " + MessageType.PROBE.name() + " from Processor" + this.id
								+ " to right Processor" + this.right.id + " !!!!!");
						this.send(msg, this.right);
					}
					if (topMsg.id > this.id && topMsg.hop >= Math.pow(2, topMsg.phase)) {
						Message msg = new Message(MessageType.REPLY, topMsg.id, topMsg.phase);
						msg.setSender(this);
						System.out.println();
						System.out.println(
								"##### Sending " + MessageType.REPLY.name() + " to " + topMsg.messageType.name()
										+ " from Processor" + this.id + " to left Processor" + this.left.id + " #####");
						this.send(msg, this.left);
					}
					if (topMsg.id < this.id) {
						System.out.println();
						System.out.println(
								"***** The message " + topMsg.id + " is swallowed at Processor" + this.id + " *****");
					}
				}

				if (topMsg.getSender() == this.right) {
					if (topMsg.id == this.id) {
						if (!this.isLeader) {
							System.out.println();
							System.out.println("------ The leader is Processor" + this.id + " ------");
							System.out.println();
							System.out.println("EXECUTION COMPLETE!!!!");
							this.isLeader = true;
						}
						System.exit(0);
					}
					if (topMsg.id > this.id && topMsg.hop < Math.pow(2, topMsg.phase)) {
						Message msg = new Message(MessageType.PROBE, topMsg.id, topMsg.phase, topMsg.hop + 1);
						msg.setSender(this);
						System.out.println();
						System.out.println("!!!!! Forwarding " + MessageType.PROBE.name() + " from Processor" + this.id
								+ " to left Processor" + this.left.id + " !!!!!");
						this.send(msg, this.left);
					}
					if (topMsg.id > this.id && topMsg.hop >= Math.pow(2, topMsg.phase)) {
						Message msg = new Message(MessageType.REPLY, topMsg.id, topMsg.phase);
						msg.setSender(this);
						System.out.println();
						System.out.println("##### Sending " + MessageType.REPLY.name() + " to "
								+ topMsg.messageType.name() + " from Processor" + this.id + " to right Processor"
								+ this.right.id + " #####");
						this.send(msg, this.right);
					}
					if (topMsg.id < this.id) {
						System.out.println();
						System.out.println(
								"***** The message " + topMsg.id + " is swallowed at Processor" + this.id + " *****");
					}
				}
				break;

			case REPLY:
				if (topMsg.getSender() == this.left) {
					if (topMsg.id != this.id) {
						Message msg = new Message(MessageType.REPLY, topMsg.id, topMsg.phase);
						msg.setSender(this);
						this.send(msg, this.right);
					} else if (!this.hasReceivedReply) {
						this.hasReceivedReply = true;
					} else {
						System.out.println();
						System.out.println(
								"^^^^^ The phase" + topMsg.phase + " winner is Processor" + this.id + " ^^^^^");
						System.out.println();
						Message msg = new Message(MessageType.PROBE, this.id, topMsg.phase + 1, 1);
						msg.setSender(this);
						this.hasReceivedReply = false;
						this.send(msg);
					}
				}

				if (topMsg.getSender() == this.right) {
					if (topMsg.id != this.id) {
						Message msg = new Message(MessageType.REPLY, topMsg.id, topMsg.phase);
						msg.setSender(this);
						this.send(msg, this.left);
					} else if (!this.hasReceivedReply) {
						hasReceivedReply = true;
					} else {
						System.out.println();
						System.out.println(
								"^^^^^ The phase" + topMsg.phase + " winner is Processor" + this.id + " ^^^^^");
						System.out.println();
						Message msg = new Message(MessageType.PROBE, this.id, topMsg.phase + 1, 1);
						msg.setSender(this);
						this.hasReceivedReply = false;
						this.send(msg);
					}
				}

				break;
			}
		}
	}
}