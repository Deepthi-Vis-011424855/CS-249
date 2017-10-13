package HW6_ChandyLamport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Performs all the processor related tasks
 *
 * @author Sample
 * @version 1.0
 */


public class Processor implements Observer {
    int id;
    boolean flag = true;

    List<Buffer> inChannels = new ArrayList<>();

    /**
     * List of output channels
     * //TODO: Homework: Use appropriate list implementation and replace null assignment with that
     *
     */
    List<Buffer> outChannels = new ArrayList<>();

    /**
     * This is a map that will record the state of each incoming channel and all the messages
     * that have been received by this channel since the arrival of marker and receipt of duplicate marker
     * //TODO: Homework: Use appropriate Map implementation.
     */
    Map<Buffer, List<Message>> channelState = new HashMap<>();

    /**
     * This map can be used to keep track of markers received on a channel. When a marker arrives at a channel
     * put it in this map. If a marker arrives again then this map will have an entry already present from before.
     * Before  doing a put in this map first do a get and check if it is not null. ( to find out if an entry exists
     * or not). If the entry does not exist then do a put. If an entry already exists then increment the integer value
     * and do a put again.
     */
    Map<Processor, Integer> channelMarkerCount = null;

    public Processor (int id) {
      this.id=id;
    }
    
    /**
     * @param id of the processor
     */
  public Processor(int id, List<Buffer> inChannels, List<Buffer> outChannels) {
    this.id = id;
    this.inChannels = inChannels;
    this.outChannels = outChannels;
    channelMarkerCount = new HashMap <Processor, Integer>();
    for(int index=0;index<inChannels.size();index++) {
      inChannels.get(index).addObserver(this);
    }
  }

    /**
     * This is a dummy implementation which will record current state
     * of this processor
     */
    public void recordMyCurrentState() {
      System.out.println();
        System.out.println("P"+this.id+": Recording my registers... ");
        System.out.println("P"+this.id+": Recording my program counters...");
        System.out.println("P"+this.id+": Recording my local variables...");
        System.out.println();
    }

    /**
     * THis method marks the channel as empty
     * @param channel
     */
    public void recordChannelAsEmpty(Buffer channel) {

        channelState.put(channel, Collections.emptyList());

    }

    /**
     * You should send a message to this recording so that recording is stopped
     * //TODO: Homework: Move this method recordChannel(..) out of this class. Start this method in a
     *                  separate thread. This thread will start when the marker arrives and it will stop
     *                  when a duplicate marker is received. This means that each processor will have the
     *                  capability to start and stop this channel recorder thread. The processor manages the
     *                  record Channel thread. Processor will have the ability to stop the thread.
     *                  Feel free to use any java concurrency  and thread classes to implement this method
     *
     *
     * @param channel The input channel which has to be monitered
     */


    /**
     * Overloaded method, called with single argument
     * This method will add a message to this processors buffer.
     * Other processors will invoke this method to send a message to this Processor
     *
     * @param message Message to be sent
     */
    public void sendMessgeTo(Message message, Buffer channel) {
      message.setFrom(this);
      channel.saveMessage(message);

    }

    /**
     *
     * @param fromChannel channel where marker has arrived
     * @return true if this is the first marker false otherwise
     */
    public boolean isFirstMarker() {
        //TODO: Implemetent this method
        //[ Hint : Use the channelMarkerCount]
      System.out.println("Checking if P" + this.id + " has received MARKER for the first time");
      if(!channelMarkerCount.containsKey(this)) {
        channelMarkerCount.put(this, 0);
        return true;
      } else {
        channelMarkerCount.put(this, channelMarkerCount.get(this) + 1);
        
        return false;
      }
        
    }

    /**
     * Gets called when a Processor receives a message in its buffer
     * Processes the message received in the buffer
     */
    public void update(Observable observable, Object arg) {
        Message message = (Message) arg;
        Processor sender = message.getFrom();
        Processor current = this;
        
          
        if (message.getMessageType().equals(MessageType.MARKER)) {
         
          Buffer fromChannel = (Buffer) observable; 
            RecordApplicationMessages recordMsg = null;
            // We need fromChannel to stop recording from (say) 2 and 3 on channel 12 and 13. 
            //i.e incoming channel of 2 should remove P12 and incoing channel of 3 should remove P13
            
          
            
            //TODO: homework Record from Channel as Empty
            //TODO: add logic here so that if the marker comes back to the initiator then it should stop recording
            if (isFirstMarker()) {
              this.recordMyCurrentState();
              
              channelMarkerCount.put(this, channelMarkerCount.get(this) + 1);
              System.out.println();
                
              recordChannelAsEmpty(fromChannel);
              System.out.println("Recored channel " + fromChannel.getLabel() + " as empty");
                // 2 channels for senders inChannel. Use forloop here to recordChannel, i.e record message on that channel
                
              for(int idx=0; idx<current.inChannels.size(); idx++) {
                System.out.println("Sending Marker from P" + this.id + " to " + outChannels.get(idx).getLabel());
                
                recordMsg =  new RecordApplicationMessages(inChannels.get(idx), this);
                recordMsg.setName(inChannels.get(idx).getLabel());
                recordMsg.start();
              } 
              
              Message marker = new Message (MessageType.MARKER);
                
                for(int idx=0; idx<current.outChannels.size(); idx++) {
                  if(channelState.containsKey(outChannels.get(idx))) {
                    if(!channelState.get(outChannels.get(idx)).isEmpty()){
                      try {
                        Thread.sleep(1000);
                      } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                      }
                      this.sendMessgeTo(marker, outChannels.get(idx));
                      // We need to call recordChannel here to start recoding messages on incoming channel for current processor
                      
                    }
                  } else {
//                    System.out.println("Sending Marker for the first time on channel " + outChannels.get(idx).getLabel());
                    channelState.put(outChannels.get(idx), new ArrayList<Message>() );
                    try {
                      Thread.sleep(1000);
                    } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                    }
                    this.sendMessgeTo(marker, outChannels.get(idx));
                  }
                  
                }
                System.out.println();
        
            } else {
              try{
                System.out.println("Duplicate MARKER. Stop recording on all incoming channels of Processor P" + this.id);
                
              } catch (Exception e) {
                e.printStackTrace();
              }
             }
  
        }
        else{
            if (message.getMessageType().equals(MessageType.ALGORITHM)) {
//                System.out.println("Processing Algorithm message....");
            }  //There is no other type
        }


    }

    public void initiateSnapShot() {
      RecordApplicationMessages recordMsg;
        recordMyCurrentState();
         Message marker = new Message (MessageType.MARKER);
        marker.setFrom(this);
        channelMarkerCount.put(this, 1);
        
        for(int idx=0; idx<this.inChannels.size(); idx++) {
          recordMsg =  new RecordApplicationMessages(inChannels.get(idx), this);
          recordMsg.setName(inChannels.get(idx).getLabel());
          recordMsg.start();
        }
          
        for(int idx=0; idx<this.outChannels.size();idx++) {
          System.out.println("Sending Marker from P" + this.id + " to " + outChannels.get(idx).getLabel());
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          this.sendMessgeTo(marker, outChannels.get(idx));
        }
      }



}
