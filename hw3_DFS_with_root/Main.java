package hw3_DFS_with_root;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tphadke on 8/29/17.
 */
public class Main {
    Map <Processor, List<Processor> > graph ;
    Processor p0, p1, p2, p3, p4, p5;
    
    public  Main() {
        init();
    }

    public static void main (String args[]) {
        Main m = new Main();

        /**
         * Choosing Processor 0 as root and setting itself as sender
         * using the setSender() process.
         * Sending message M to Processor 0 to start the algorithm.
         */
        
        System.out.println("Setting root processor as p0...");
        System.out.println();	
        m.p0.messageBuffer.setSender(m.p0);
        m.p0.sendMessgeToMyBuffer(Message.M);
        
        //Calling the display() function to print the final spanning tree formed
        m.display();
        
        System.out.println();
        System.out.println("Terminating....");
        
    }
    
    /**
     * Populating the graph as per the given graph structure.
     * Creating even the unexplored list for each processor.
     */
    public void init(){
    		graph = new HashMap<Processor, List<Processor> >();
    		
    		this.p0 = new Processor();
    		p0.id = 0;
    		
    		this.p1 = new Processor();
    		p1.id = 1;
    		
    		this.p2 = new Processor();
    		p2.id = 2;
    		
    		this.p3 = new Processor();
    		p3.id = 3;
    		
    		this.p4 = new Processor();
    		p4.id = 4;
    		
    		this.p5 = new Processor();
    		p5.id = 5;
    		
    		p0.unexplored.addAll(Arrays.asList(p1,p2,p3));
    		p1.unexplored.addAll(Arrays.asList(p0,p2,p4));
    		p2.unexplored.addAll(Arrays.asList(p0,p1,p5));
    		p3.unexplored.addAll(Arrays.asList(p0));
    		p4.unexplored.addAll(Arrays.asList(p1,p5));
    		p5.unexplored.addAll(Arrays.asList(p2,p4));
    		
    		graph.put(p0, p0.unexplored);
    		graph.put(p1, p1.unexplored);
    		graph.put(p2, p2.unexplored);
    		graph.put(p3, p3.unexplored);
    		graph.put(p4, p4.unexplored);
    		graph.put(p5, p5.unexplored);
    }
    
    /**
     * A function to display the final spanning tree
     */
    public void display() {
    		System.out.println("The spanning tree formed is:");
        System.out.println("------------------------------------------");
        for (Processor processor : this.graph.keySet())
        {
            	System.out.print("Processor "+processor.id  + "'s children: ");
            	for(int i=0 ; i<processor.children.size() ; i++) {
            		System.out.print(processor.children.get(i).id + "  ");
            }
            System.out.println();
        }
    }
}
