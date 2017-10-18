package HW6_ChandyLamport;

import java.util.ArrayList;
import java.util.List;

public class RecordApplicationMessages extends Thread {
	Buffer channel;
	Processor p;
	List<Message> recordedMessagesSinceMarker;

	public RecordApplicationMessages(Buffer channel, Processor p) {
		this.channel = channel;
		this.p = p;
		recordedMessagesSinceMarker = new ArrayList<>();
	}

	@Override
	public void run() {
		int lastIdx = channel.getTotalMessageCount() - 1;
		int current = lastIdx;
		int count;

		try {
			while (!this.isInterrupted()) {
				count = channel.getTotalMessageCount() - 1;
				if (current < count) {

					Message message = channel.getMessage(current);
					recordedMessagesSinceMarker.add(message);
					current++;
				}
			}
			System.out.println("Channel " + channel.getLabel() + " interrupted");
			System.out.println("The messages recorded in the channel are:");
			for (int i = 0; i < recordedMessagesSinceMarker.size(); i++) {
				System.out.println(recordedMessagesSinceMarker.get(i));
			}
			System.out.println();
		} catch (Exception e) {
			System.out.println("ERROR: Channel " + channel.getLabel() + " interrupted");
		}

	}

}