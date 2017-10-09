package HW5_MaximumConsistentCut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) {
		//Main m = new Main();
		Algorithm algo = new Algorithm();
		int cut[]= new int[2];
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		algo.executionPlanForP0();
		algo.executionPlanForP1();
		
		algo.printStoreArrays();
		
		System.out.println("Enter the values for a cut");
		try {
      cut[0]= Integer.parseInt(in.readLine());
      cut[1]= Integer.parseInt(in.readLine());
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
		
		algo.printMaxCut(cut);
		
	}
}