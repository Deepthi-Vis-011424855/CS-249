package Main;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import Cassandra.CassandraConnector;
import Cassandra.CassandraDao;

class Main {
	public static void main(String[] args) {
		
		final CassandraConnector client = new CassandraConnector();
		final String ipAddress = args.length > 0 ? args[0] : "localhost";
		final int port = args.length > 1 ? Integer.parseInt(args[1]) : 9042;
		System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");
		client.connect(ipAddress, port);
		client.getSession().execute("use myks;");
		
		CassandraDao read = new CassandraDao();
		ResultSet res = read.getPatientInfo(1);
		while (!res.isExhausted()) {
			Row row = res.one();
			System.out.println(row);
		}	
	}
}