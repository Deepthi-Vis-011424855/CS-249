package Cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
 
public class HazelCastServer {
	public static Map<Integer, String> customerMap;
	public static HazelcastInstance instance;
	
	public HazelCastServer() {
		init();
	}
	
	public void init() {
		Config cfg = new Config();
        instance = Hazelcast.newHazelcastInstance(cfg);
        customerMap = instance.getMap("custom");
	}

 
    public static void main(String[] args) {
    	
//    	
    		HazelCastServer server = new HazelCastServer();
    		System.out.println("Hazelcast server started");
//    		server.putInMap("trial");
//    		
//    		
//        customerMap.put(1, "ICU");
//        customerMap.put(2, "Casual");
//        customerMap.put(3, "Casual");
//
//        System.out.println("Map Size:" + customerMap.size()); 
//        Set<Entry<Integer,String>> customers = customerMap.entrySet();
//
//        for (Iterator<Entry<Integer, String>> iterator = customers.iterator(); iterator.hasNext();) {
//            Entry<Integer, String> entry = (Entry<Integer, String>) iterator.next();
//            System.out.println("Customer Id : "+ entry.getKey()+" Customer Name : "+entry.getValue());
//      }
    }
    
//    public void putInMap(String trial) {
//		customerMap = instance.getMap("custom");
//		
//		customerMap.put(11, trial);
//		
//		System.out.println("Map Size:" + customerMap.size()); 
//      Set<Entry<Integer,String>> customers = customerMap.entrySet();
//
//      for (Iterator<Entry<Integer, String>> iterator = customers.iterator(); iterator.hasNext();) {
//          Entry<Integer, String> entry = (Entry<Integer, String>) iterator.next();
//          System.out.println("Customer Id : "+ entry.getKey()+" Customer Name : "+entry.getValue());
//    }
//    }
    
   
    
    //add a method to put in hazelcast map
    
    //add a method to clear the map completely
}