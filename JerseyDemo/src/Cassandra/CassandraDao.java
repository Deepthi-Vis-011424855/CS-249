package Cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class CassandraDao {
	Session conn;

	public CassandraDao() {

		init();

	}

	public void init() {
		final CassandraConnector client = new CassandraConnector();
		final String ipAddress = "localhost";
		final int port = 9042;
		System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");
		conn = client.connect(ipAddress, port);
		conn.execute("use myks;");
	}
	
	public ResultSet getAllPatients() {
		Statement stmt = QueryBuilder
	            .select()
	            .all()
	            .from("myks", "happy_patients");
		
		ResultSet res = conn.execute(stmt);
	            
		if (res == null) {
			System.out.println("ERROR while retrieving all rows from DB...");
			return null;
		} else {
			return res;
		}
	}

	public ResultSet getPatientInfo(int id) {
		System.out.println("Inserting new record where ID = " + id);
		ResultSet res = conn.execute("SELECT * FROM happy_patients where id = ?", id);
		if (res == null) {
			System.out.println("The given ID not in the database. Please provide the correct ID...");
			return null;
		} else {
			return res;
		}
	}

	public void putPatientInfo(int id, String address, String diagnosis, String dob, String name, String phone,
			String treatment) {
		// conn = new CassandraConnector();
		System.out.println("Inserting data into database...");
		System.out.println();
		System.out.println("The data to insert are: ");
		System.out.println("ID = " + id);
		System.out.println("Address = " + address);
		System.out.println("Diagnosis = " + diagnosis);
		System.out.println("DOB = " + dob);
		System.out.println("Name = " + name);
		System.out.println("Phone: " + phone);
		System.out.println("Treatment = " + treatment);

		ResultSet test = conn.execute("select * from happy_patients where id = ?", id);

		if (test.all().size() > 0) {
			System.out.println("Patient data with ID = " + id + " is already present in the database...");
		} else {
			conn.execute(
					"insert into happy_patients (id, address, diagnosis, dob, name, phone, treatment) "
							+ "values (?, ?, ?, ?, ?, ?, ?)",
					id, address, diagnosis, dob, name, phone, treatment);
		}
	}

	public void updatePatientInfo(int id, String field, String value) {
		// conn = new CassandraConnector();
		System.out.println("Updating data where id = " + id);
		System.out.println("field: " + field);
		System.out.println("value: " + value);
		ResultSet test = conn.execute("select * from happy_patients where id = ?", id);
		if (test.all().size() <=0) {
			System.out.println("There is no data for the given ID = " + id + " in the database");
		} else {
			
			Statement exampleQuery = QueryBuilder.update("myks","happy_patients")
					.with(QueryBuilder.set(field, value))
			        .where(QueryBuilder.eq("id", id));
			conn.execute(exampleQuery);
			
			System.out.println("Cassandra Dao class> Executed update query for DB");
		}
	}

	public void deletePatientInfo(int id) {
		System.out.println("Deleting patient record where id");
		ResultSet test = conn.execute("select * from happy_patients where id = ?", id);
		if (test.all().size() <=0) {
			System.out.println("There is no data for the given ID = " + id + " in the database");
		} else {
			Statement exampleQuery = QueryBuilder.delete()
					.from("myks","happy_patients")
					.where(QueryBuilder.eq("id",id));
			conn.execute(exampleQuery);
		}
	}

}