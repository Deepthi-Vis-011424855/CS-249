package Patient;

import java.util.List;
import java.util.ListIterator;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import Cache.HazelCastClient;
import Cassandra.CassandraConnector;
import Cassandra.CassandraDao;
import Messaging.Producer;
import Patient.PolicyServerClient;

@Path("/patient")
public class PatientDemo {
	HazelCastClient hcClient;
	PolicyServerClient psClient;
//	@GET
//	@Produces(MediaType.TEXT_XML)
//	@Path("{name}")
//	public String sayHelloInXml(@PathParam("name") String name) {
//		
//		String response = "<?xml version ='1.0'?>"
//				+ " <response> <hello> Hey! I'm alive!! </hello>"
//				+ "<name>" + name + "</name> </response>";
//		return response;
//	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getPatient/{id}")
	public String sayHelloInJson(@PathParam("id") String idString) {
		int id = Integer.parseInt(idString);
		String response;
		
		hcClient = new HazelCastClient();
		if(hcClient.getPatientInfo(id)!=null) {
			response= "PatientDemo > Hazelcast Cache contains: "+ hcClient.getPatientInfo(id);
		} else {
			CassandraDao cassdao = new CassandraDao();
			ResultSet res = cassdao.getPatientInfo(id);
			List<Row> rows = res.all();
			int rowID = 0;
			String rowName = "";
			String rowAddress = "";
			String rowDob = "";
			String rowPhone = "";
			String rowTreat = "";
			String rowDiag = "";
			
			for(Row thisRow: rows) {
				rowID = thisRow.getInt("id");
				rowName = thisRow.getString("name");
				rowAddress = thisRow.getString("address");
				rowDob = thisRow.getString("dob");
				rowPhone = thisRow.getString("phone");
				rowTreat = thisRow.getString("treatment");
				rowDiag = thisRow.getString("diagnosis");
						
			}
			
			response = "{ "+rowID+","+ rowName + " , " + rowAddress + " , " + rowDob + " , " + rowPhone + " , " + rowTreat
			+ " , " + rowDiag + " }";
			
		}
			return response;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addPatient/{details}")
	public String addPatient(@PathParam("details") String details) {
		String splitInput[] = details.split(":");
		CassandraDao cd = new CassandraDao();
		Row row;
		psClient = new PolicyServerClient();
		hcClient = new HazelCastClient();
		
		cd.putPatientInfo(Integer.parseInt(splitInput[0]), splitInput[4], splitInput[5], splitInput[3], 
				splitInput[1], splitInput[2], splitInput[6]);
		System.out.println("PatientDemo class > Added data in database");
		
		ResultSet toPutInHC = cd.getPatientInfo(Integer.parseInt(splitInput[0]));
		
		System.out.println("Still inside PatientDemo");
		
		row = toPutInHC.one();
		
		System.out.println("******* "+ row+" *********");
		
		
		System.out.println("The policy is: "+psClient.getPolicy());
		System.out.println("The treatment is: "+splitInput[6]);
		
		if(splitInput[6].equals(psClient.getPolicy())) {
			hcClient.putInMap(Integer.parseInt(splitInput[0]), row);
			System.out.println("PatientDemo class> Added the data in cache too");
		}
		
		Producer prod = new Producer();
		prod.broadcastMsg("Added a Patient. "+ row);
				
		return "true";
		
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updatePatient/{details}")
	public String updatePatient(@PathParam("details") String details) {
		String splitInput[] = details.split(":");
		
		CassandraDao cd = new CassandraDao();
		Row row;
		psClient = new PolicyServerClient();
		hcClient = new HazelCastClient();
		
		ResultSet toPutInHCBefore = cd.getPatientInfo(Integer.parseInt(splitInput[0]));
		Row rowBefore = toPutInHCBefore.one();
		
		
		cd.updatePatientInfo(Integer.parseInt(splitInput[0]), splitInput[1], splitInput[2]);
		System.out.println("Patient Demo class > Database updated");
		ResultSet toPutInHC = cd.getPatientInfo(Integer.parseInt(splitInput[0]));
		row = toPutInHC.one();
		
		
		if(splitInput[1].equals("treatment")) {
			if(splitInput[2].equals(psClient.getPolicy())) {
				hcClient.putInMap(Integer.parseInt(splitInput[0]), row);
				System.out.println("PatientDemo class> Updated data and added the data in cache too");
			} else if(rowBefore.getString(6).equals(psClient.getPolicy()) 
					&& !(splitInput[2].equals(psClient.getPolicy()))) {
				hcClient.deleteFromMap(Integer.parseInt(splitInput[0]));
				System.out.println("PatientDemo class> Updated data and deleted the data in cache too");
			}
		} 
		
		Producer prod = new Producer();
		prod.broadcastMsg("Updated a Patient. "+ row);
		
		return "true";
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deletePatient/{details}")
	public String deletePatient(@PathParam("details") String details) {
		int id = Integer.parseInt(details);
		
		CassandraDao cd = new CassandraDao();
		psClient = new PolicyServerClient();
		hcClient = new HazelCastClient();
		
		cd.deletePatientInfo(id);
		System.out.println("Patient Demo class > Database updated");
		
			
		if(hcClient.getPatientInfo(id).length()>0) {
			hcClient.deleteFromMap(id);
			System.out.println("PatientDemo > Successfully deleted from Map");
		}
		
		Producer prod = new Producer();
		prod.broadcastMsg("Deleted patient with ID: " + id);
		
		return "true";
	}

}