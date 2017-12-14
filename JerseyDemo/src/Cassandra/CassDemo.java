package Cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import Cache.HazelCastClient;

public class CassDemo {
	Session conn;
	
	public void init() {
		final CassandraConnector client = new CassandraConnector();
		final String ipAddress = "localhost";
		final int port = 9042;
		System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");
		conn = client.connect(ipAddress, port);
		conn.execute("use myks;");
	}

	public static void main(String[] args) {
		CassDemo cd = new CassDemo();
				
		cd.init();
//		int id = 100;
//		String address = "jdsfjsfdsfsad";
//		String diagnosis = "noen";
//		String dob = "1991-09-09";
//		String name = "Deepthi";
//		String phone = "444444444";
//		String treatment = "ICU";
//		
//		cd.conn.execute("insert into happy_patients (id, address, diagnosis, dob, name, phone, treatment) "+
//				"values (?, ?, ?, ?, ?, ?, ?)", id, address, diagnosis, dob, name, phone, treatment);
//		
//		ResultSet test =  cd.conn.execute("select * from happy_patients where id = ?", 101);
//		while (!test.isExhausted()) {
//			Row row = test.one();
//			System.out.println(row);
//		}
		
		System.out.println("Testing in CASS Demo now.");
		HazelCastClient hcc = new HazelCastClient();
		
	}

}
