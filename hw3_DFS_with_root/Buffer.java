package hw3_DFS_with_root;
import java.util.Observable;

/**
 * Created by tphadke on 8/29/17.
 */
public class Buffer extends Observable {
    private Message message;
    private Processor senderProcessID;

    public Buffer() {
        //Creates an empty Buffer
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
        notifyObservers(); // This calls the update() in Processor.java
    }
}

