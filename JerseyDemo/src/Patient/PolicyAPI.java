package Patient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/policy")
public class PolicyAPI {
	
//	@GET
//	@Produces(MediaType.TEXT_XML)
//	@Path("{name}")
//	public String sayHelloInXml(@PathParam("name") String name) {
//		
//		Properties prop = new Properties();
//	    try {
//	      
//	      prop.load(new FileInputStream("src/Patient/policy.properties"));
//	    } catch (FileNotFoundException e) {
//	      e.printStackTrace();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
//	    //return prop.getProperty("fetchPolicy");
//	         
//		
//		
//		String response = "<?xml version ='1.0'?>"
//				+ " <response> <hello> Hey! I'm alive!! </hello>"
//				+ "<name>" + prop.getProperty("fetchPolicy") + "</name> </response>";
//		return response;
//	}
//	

	
	@GET
	@Path("/getPolicy")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPolicy() {
		// System.out.println(new File(".").getAbsolutePath());
		Properties prop = new Properties();
		try {
			//System.out.println(new File(".").getAbsolutePath());
			// String PROPERTIES_FILE = "policy.properties";
			System.out.println("PolicyAPI> System property: " + System.getProperty("user.dir"));
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//policyFile.properties");

			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String response = prop.getProperty("fetchPolicy");
		return response;
	}

}