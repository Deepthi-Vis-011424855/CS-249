package Cache;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
 
public class HazelCastClient {
	public static HazelcastInstance client;
	ClientConfig clientConfig;
	IMap<Integer, String> map;
	
	public HazelCastClient() {
		ClientConfig clientConfig = new ClientConfig();
        clientConfig.addAddress("127.0.0.1");
        
        client = HazelcastClient.newHazelcastClient(clientConfig);
        map = client.getMap("custom");
        
	}
	
	@SuppressWarnings({ "deprecation" })
	public String getPatientInfo(int id ) {
		printMap(map);
		String result = map.get(id);
		System.out.println("HC CLient class> You got from client !!!" + result);
		return result;
	}
	
	
	/*
	@SuppressWarnings({ "deprecation" })
	public String getPatientInfo(int id ) {
		printMap(map);

		if (map.containsKey(id)) {

			ResultSet res = map.get(id);
			List<Row> rows = res.all();
			int rowID = 0;
			String rowName = "";
			String rowAddress = "";
			String rowDob = "";
			String rowPhone = "";
			String rowTreat = "";
			String rowDiag = "";

			for (Row thisRow : rows) {
				rowID = thisRow.getInt("id");
				rowName = thisRow.getString("name");
				rowAddress = thisRow.getString("address");
				rowDob = thisRow.getString("dob");
				rowPhone = thisRow.getString("phone");
				rowTreat = thisRow.getString("treatment");
				rowDiag = thisRow.getString("diagnosis");

			}

			String response = "{ " + rowID + "," + rowName + " , " + rowAddress + " , " + rowDob + " , " + rowPhone
					+ " , " + rowTreat + " , " + rowDiag + " }";
			return response;
		} else {
			return null;
		}
		
	}
	*/
	
	
	public void putInMap(int id, Row row) {
		//init();
		int rowID = 0;
		String rowName;
		String rowAddress;
		String rowDob;
		String rowPhone;
		String rowTreat;
		String rowDiag;

		System.out.println(" !!!!!! Inside HCClient class > putInMap method : " + row ); 
			rowID = row.getInt("id");
			System.out.println("Checking: rowID: " + rowID);
			rowName = row.getString("name");
			System.out.println("name: " + rowName);
			rowAddress = row.getString("address");
			System.out.println("address: " + rowAddress);
			rowDob = row.getString("dob");
			System.out.println("dob: " + rowDob);
			rowPhone = row.getString("phone");
			System.out.println("phone: " + rowPhone);
			rowTreat = row.getString("treatment");
			System.out.println("treatment: " + rowTreat);
			rowDiag = row.getString("diagnosis");
			System.out.println("diagnosis: " + rowDiag);


		String response = "{ " + rowID + "," + rowName + " , " + rowAddress + " , " + rowDob + " , " + rowPhone
				+ " , " + rowTreat + " , " + rowDiag + " }";
		
		map.put(id, response);
		printMap(map);
	}
	
	
	
	
//	public void updateMap(int id, ResultSet input) {
//		//init();
//		if(map.containsKey(id)) {
//			System.out.println("Hazelcast client class> The map contains ID: " + id);
//			map.put(id, input);
//			printMap(map);
//		} else {
//			System.out.println("Hazelcast client class> The map does NOT contain ID: " + id);
//		}
//		
//	}
	
	public void deleteFromMap(int id) {
		//init();
		map.remove(id);
		System.out.println("Deleted from Map !" + id);
		printMap(map);
	}
	
	public void clearMap() {
		//init();
		map.clear();
		printMap(map);
	}
	
 
//    @SuppressWarnings({ "deprecation" })
//  public static void main(String[] args) {
//	  HazelCastClient hcc = new HazelCastClient();
//	  hcc.getPatientInfo(11);
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.addAddress("127.0.0.1");
//        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
//        IMap<Object, Object> map = client.getMap("custom");
//        printMap(map);
//        map.put(5, "ICU");
//        map.put(6, "Casual");
////        PolicyServerClient policyClient = new PolicyServerClient();
////        map.put(100, policyClient.getPolicy());
//        
//        if(map.containsKey(6)) {
//          System.out.println("Well, yeah I can search from here too !");
//        }
//        printMap(map);
//    }
  
    @SuppressWarnings("unchecked")
    private static void printMap(@SuppressWarnings("rawtypes") Map map){
      System.out.println("Map Size:" + map.size());
    Set<Entry<Integer,String>> customers = map.entrySet();
       for(Iterator<Entry<Integer, String>> iterator = customers.iterator(); iterator.hasNext();) {
          Entry<Integer, String> entry = (Entry<Integer, String>) iterator.next();
          System.out.println("Customer Id : "+ entry.getKey()+" Customer Name : "+entry.getValue());
      } 
    }
    
  
}