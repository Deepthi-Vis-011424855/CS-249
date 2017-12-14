package Patient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class PolicyServerClient {

	// public static void main(String args[]) {
	// Properties prop = new Properties();
	// try {
	// prop.load(new FileInputStream("policy.properties"));
	//
	// HazelCastClient cacheClient = new HazelCastClient();
	//
	// cacheClient.passToCacheServer(prop.getProperty("fetchPolicy"));
	//
	//
	// } catch (IOException e) {
	// }
	//
	// }
	
	public static void main (String args[]) {
		PolicyServerClient psc = new PolicyServerClient();
		String policy = psc.getPolicy();
	}

	public String getPolicy() {
		//System.out.println(new File(".").getAbsolutePath());

		Client client = ClientBuilder.newClient();
		System.out.println("In here!!!");
		WebTarget target = client.target("http://localhost:9090/JerseyDemo/policy/getPolicy/");
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("PolicyServerClient class> "+ response);

		return response;

	}
}