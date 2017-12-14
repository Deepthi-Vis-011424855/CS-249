package Cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraConnector {
	private Cluster cluster;
	private Session session;
	
	public Session connect(String node, int port) {
		this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
		Metadata metadata = cluster.getMetadata();
		System.out.println("Connected to cluster: "+metadata.getClusterName());
		session = cluster.connect();
		
		return session;
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public void close() {
		cluster.close();
	}
}