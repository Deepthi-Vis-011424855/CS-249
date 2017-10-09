package HW5_MaximumConsistentCut;

import java.util.Observable;
import java.util.ArrayList;
import java.util.Observer;

/**
 * Performs all the processor related tasks
 * 
 * @author Sample
 * @version 1.0
 *
 */
public class Processor implements Observer {
  Buffer messageBuffer;
  Integer id;
  VectorClock vc; // This is the current vector clock
  ArrayList<int[]> storeArray;

  /**
   * Initializes the processor with id, children and unexplored lists. Adds
   * himself in the observers list.
   * 
   * @param id
   *          of the processor
   */
  public Processor(int id, int totalProcesors) {
    messageBuffer = new Buffer();
    this.id = id;
    messageBuffer.addObserver(this);
    vc = new VectorClock(2);
    storeArray = new ArrayList<int[]>();
  }

  /**
   * Overloaded method, called with single argument This method will add a
   * message to this processors buffer. Other processors will invoke this method
   * to send a message to this Processor
   * 
   * @param message
   *          Message to be sent
   */
  public void sendMessageToMyBuffer(Message message) {
    messageBuffer.setMessage(message);

  }

  /**
   * Gets called when a node receives a message in it buffer Processes the
   * message received in the buffer
   */
  public void update(Observable observable, Object arg) {
    calculateVectorClocks(observable, arg);
    this.addToStoreArray(this.vc);
  }

  /**
   * A method to add vector clock to storeArray.
   * 
   * @param incoming
   *          The vector clock to be added
   */
  public void addToStoreArray(VectorClock incoming) {
    int tempArr[] = new int[2];
    tempArr[0] = incoming.vc[0];
    tempArr[1] = incoming.vc[1];
    storeArray.add(tempArr);
  }

  /**
   * A method to print the storeArray
   */
  public void printStoreArray() {
    System.out.println("This is the store array for Processor " + this.id);
    int size = storeArray.size();
    for (int idx = 0; idx < size; idx++) {
      System.out
          .println("[" + storeArray.get(idx)[0] + "," + storeArray.get(idx)[1]  + "]");
    }

  }

  /**
   * This method performs the appropriate operation according to the
   * Message.type received in the message buffer
   */
  public void calculateVectorClocks(Observable observable, Object arg) {

    Message m = messageBuffer.getMessage();
    switch (m.messageType) {

    case COMPUTATION:

      this.vc.updateAt(this.id, vc.vc[this.id] + 1);

      break;

    case SEND:

      this.vc.updateAt(this.id, vc.vc[this.id] + 1);

      break;

    case RECIEVE:

      this.vc.updateAt(this.id, vc.vc[this.id] + 1);
      for (int i = 0; i < vc.vc.length; i++) {
        this.vc.vc[i] = Math.max(this.vc.vc[i], m.vc.vc[i]);
      }
    }

  }

  /**
   * This method computes the max cut based on the input cut
   */

  public int calculateMaxCut(int cut[]) {
    int index = cut[this.id];
    VectorClock processorVc;
    VectorClock cutVc = new VectorClock(cut);
    int ans = 0;

    for (int idx = index - 1; idx >= 0;) {
      processorVc = new VectorClock(storeArray.get(idx));

      if (processorVc.compareTo(cutVc) == 0 || processorVc.compareTo(cutVc) == -1) {
        ans = processorVc.vc[this.id];
        break;

      } else if (processorVc.compareTo(cutVc) == 1) {
        idx--;
      } else {
        idx--;
      }

    }
    return ans;
  }

}
