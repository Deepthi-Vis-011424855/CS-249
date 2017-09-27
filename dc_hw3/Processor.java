package dc_hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by tphadke on 8/29/17.
 */
public class Processor implements Observer {
    //Each processsor has a message Buffer to store messages
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
        //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
        unexplored = new ArrayList<>();
        //Each processor is observing itself;
        messageBuffer.addObserver(this);
    }

    //This method will only be used by the Processor
    private void removeFromUnexplored(Processor p){
        //TODO: implement removing one processor from the list of Children
    		unexplored.remove(p);
    }

    //This method will add a message to this processors buffer.
    //Other processors will invoke this method to send a message to this Processor
    public void sendMessgeToMyBuffer(Message message){
    		System.out.println("Sending message " +message+ " from Processor " +messageBuffer.getSender().id+ " to Processor " +this.id);
    		messageBuffer.setMessage(message);
    }


    //This is analogous to recieve method.Whenever a message is dropped in its buffer this Pocesssor will respond
    //TODO: implement the logic of receive method here
    //      Hint: Add switch case for each of the conditions given in receive
    public void update(Observable observable, Object arg) {
    		Processor sourceProcessor = (Processor) arg;
    		Message m = messageBuffer.getMessage();
    		
    		switch (m) {
    		case M:
    			if (this == this.parent) {
    				explore();
    			}
    			if (this.parent == null) {
    				this.setParent(sourceProcessor);
    				this.removeFromUnexplored(sourceProcessor);
    				explore();
    			} else if (sourceProcessor != this){
    				sourceProcessor.messageBuffer.setSender(this);
    				sourceProcessor.sendMessgeToMyBuffer(Message.ALREADY);
    				this.removeFromUnexplored(sourceProcessor);
    			}
    			break;
    			
    		case ALREADY:
    			explore();
    			break;
    			
    		case PARENT:
    			System.out.println("Adding Processor " +sourceProcessor.id+ " as Processor " +this.id+ "'s child...");
    			this.children.add(sourceProcessor);
    			explore();
    			break;			
    		}
    }

    private void explore() {
        //TODO: implement this method.
    		System.out.println();
    		if (unexplored.size() != 0 ) {
    			currentExplored = unexplored.get(unexplored.size() - 1);
    			System.out.println("Processor chosen from unexplored list of " +this.id+ " : " +currentExplored.id+ " will be removed...");
    			//System.out.println("Removing Processor "+currentExplored.id+" from unexplored list of Processor " +this.id+ " ...");
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


