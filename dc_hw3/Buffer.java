package dc_hw3;
import java.util.Observable;

/**
 * Created by tphadke on 8/29/17.
 */
public class Buffer extends Observable {
    private Message message;
    private Processor senderProcessID;

    public Buffer() {
        //Create an empty Buffer
    }
    public Buffer(Message message) {
        this.message = message;
    }

    public Message  getMessage() {
        return message;
    }
    
    public void setSender(Processor processor) {
    		this.senderProcessID = processor;
    }
    public Processor getSender() {
    		return this.senderProcessID;
    }
    public void setMessage(Message message) {
        this.message = message;
        setChanged();
        notifyObservers(senderProcessID); // This calls the update() in Processor.java
    }
}

