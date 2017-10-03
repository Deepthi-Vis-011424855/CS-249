package hw3_DFS_with_root;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by tphadke on 8/29/17.
 */
public class Processor implements Observer {
    //Each processor has a message Buffer to store messages
    Buffer messageBuffer ;
    Integer id ;
    List<Processor> children ;
    //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
    List<Processor> unexplored ;
    Processor parent = null;
    Processor currentExplored = null;
    
    public void setParent(Processor p) {
    		this.parent = p;
    }

    public Processor() {
        messageBuffer = new Buffer();
        id = Integer.MIN_VALUE; //This is an invalid value. Since only +ve values are acceptable as processor Ids.
        children = new ArrayList<>();
        unexplored = new ArrayList<>();
        //Each processor is observing itself;
        messageBuffer.addObserver(this);
    }

    //This method will only be used by the Processor
    private void removeFromUnexplored(Processor p){
        //Removing the corresponding processor from the unexplored list of the processor.
    		unexplored.remove(p);
    }
    
    /**
     * This method will add a message to this processors buffer.
     * Other processors will invoke this method to send a message to this Processor.
     */
    public void sendMessgeToMyBuffer(Message message){
    		System.out.println("Sending message " +message+ " from Processor " +messageBuffer.getSender().id+ " to Processor " +this.id);
    		messageBuffer.setMessage(message);
    }


    /**This is analogous to receive method.
     * Whenever a message is dropped in its buffer this Processor will respond
     */
    public void update(Observable observable, Object arg) {
    		/** Type-casting the observable object to the Buffer type
    		 * 	This is done to get access to the source processor which is sending the message
    		 */
    		Buffer buff = (Buffer) observable;
    		Message m = buff.getMessage();
    		
    		switch (m) {
    		case M:
    			if (this.parent == null) {
    				this.setParent(buff.getSender());
    				this.removeFromUnexplored(buff.getSender());
    				explore();
    			} else {
    				this.removeFromUnexplored(buff.getSender());
    				//Setting the sender processor before a message is sent
    				buff.getSender().messageBuffer.setSender(this);
    				buff.getSender().sendMessgeToMyBuffer(Message.ALREADY);
    			}
    			break;
    			
    		case ALREADY:
    			explore();
    			break;
    			
    		case PARENT:
    			System.out.println("Adding Processor " +this.messageBuffer.getSender().id+ " as Processor " +this.id+ "'s child...");
    			this.children.add(buff.getSender());
    			explore();
    			break;			
    		}
    }
    
    /**
     * This function explore the unexplored list of a processor.
     * Chooses one processor, removes it from the unexplored list and,
     * sends the appropriate message to its buffer
     */
    private void explore() {
    		System.out.println();
    		if (unexplored.size() != 0 ) {
    			currentExplored = unexplored.get(unexplored.size() - 1);
    			System.out.println("Processor chosen from unexplored list of " +this.id+ " : " +currentExplored.id+ " will be removed...");
    			removeFromUnexplored(currentExplored);
    			currentExplored.messageBuffer.setSender(this);
    			currentExplored.sendMessgeToMyBuffer(Message.M);
    		} else {
    			if (this != this.parent) {
    				this.parent.messageBuffer.setSender(this);
    				this.parent.sendMessgeToMyBuffer(Message.PARENT);
    			}
    		} 
    }
}


