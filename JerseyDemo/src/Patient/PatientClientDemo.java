package Patient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import Cache.HazelCastClient;
import Cassandra.CassandraDao;

public class PatientClientDemo {
	
	public static void displayMenu() {
		System.out.println(new File(".").getAbsolutePath());
		System.out.println("What would you like to do? Enter the correct option");
		System.out.println("1. Get Patient Info");
		System.out.println("2. Add Patient Info");
		System.out.println("3. Update Patient Info");
		System.out.println("4. Delete Patient Info");
		System.out.println("5. Exit");
		
	}
	
	public static void loadIntoCache() {
		CassandraDao cd = new CassandraDao();
		PolicyServerClient psClient = new PolicyServerClient();
		HazelCastClient hcClient = new HazelCastClient();
		
		ResultSet rs = cd.getAllPatients();
		Row row;
		int id;
		String treatmentType;
		
		while(!rs.isExhausted()) {
			row = rs.one();
			id = row.getInt(0);
			treatmentType = row.getString(6);
			if(treatmentType.equals(psClient.getPolicy())) {
				hcClient.putInMap(id , row);
			}
			
		}
		System.out.println("**** INITIALIZED CACHE *****");
	}

	public static void main(String[] args) {
		loadIntoCache();
		
		int opt = 0;
		do {
			displayMenu();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
				opt = Integer.parseInt(in.readLine());
				
				switch(opt) {
				case 1:
					System.out.println("Enter Patient ID you wish to look ");
					int lookForId = Integer.parseInt(in.readLine());
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target("http://localhost:8080/JerseyDemo/patient/getPatient/" + lookForId);
					System.out.println(target.request(MediaType.APPLICATION_JSON).get(String.class));
					break;
					
				case 2:
					System.out.println("Enter the ID");
					int newId = Integer.parseInt(in.readLine());
					System.out.println("Enter the name");
					String name = in.readLine();
					System.out.println("Enter the phone number");
					String phone = in.readLine();
					System.out.println("Enter the date of birth in yyyy-mm-dd");
					String dob = in.readLine();
					System.out.println("Enter the Address");
					String addr = in.readLine();
					System.out.println("Enter the diagnosis");
					String diag = in.readLine();
					System.out.println("Enter the treatment");
					String treat = in.readLine();
					
					String input = newId+":"+name+":"+phone+":"+dob+":"+addr+":"+diag+":"+treat;
					
					client = ClientBuilder.newClient();
					target = client.target("http://localhost:8080/JerseyDemo/patient/addPatient/" + input);
					if(target.request(MediaType.APPLICATION_JSON).post(Entity.entity(input, MediaType.APPLICATION_JSON))!=null) {
						System.out.println("Successfully added to Map");
					} else {
						System.out.println("ERROR occured while adding to map");
					}
					
					break;
					
				case 3:
					System.out.println("Enter the ID of the person to change his info");
					int updateId = Integer.parseInt(in.readLine());
					System.out.println("Enter the field name you wish to update");
					String field = in.readLine();
					System.out.println("Enter the new value");
					String newValue = in.readLine();
					
					input = updateId+":"+field+":"+newValue;
					
					client = ClientBuilder.newClient();
					target = client.target("http://localhost:8080/JerseyDemo/patient/updatePatient/" + input);
					
					if(target.request(MediaType.APPLICATION_JSON).post(Entity.entity(input, MediaType.APPLICATION_JSON))!=null) {
						System.out.println("Successfully Updated");
					} else {
						System.out.println("ERROR occured while Updating");
					}
					
					break;
					
				case 4:
					System.out.println("Enter the ID of the person to delete his info");
					int deleteId = Integer.parseInt(in.readLine());
					
					client = ClientBuilder.newClient();
					target = client.target("http://localhost:8080/JerseyDemo/patient/deletePatient/" + deleteId);
					
					if(target.request(MediaType.APPLICATION_JSON).post(Entity.entity(deleteId, MediaType.APPLICATION_JSON))!=null) {
						System.out.println("Successfully Updated");
					} else {
						System.out.println("ERROR occured while Updating");
					}
					
					break;
					
				default:
					
				}
				
					
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
			
		} while (opt!=5);
	}

}