package hw4_Convergecast;
import java.util.*;

public class Main {
	TreeNode root, p1, p2, p3, p4, p5, p6, p7, p8;
	
	public Main() {
		init();
	}
	public static void main (String[] args) {
		Main m = new Main();
		
		int max_value = m.ConvergeCastAlgo(m.root);
		System.out.println("The maximum value in the given tree is: "+max_value);
		
		System.out.println();
		
		System.out.println("Printing all the values from all the nodes concatenated");
		System.out.println();
		m.helper(m.root);	
	}
	
	public void init() {
		root = new TreeNode(2);
		p1 = new TreeNode(7);
		p2 = new TreeNode(5);
		p3 = new TreeNode(2);
		p4 = new TreeNode(6);
		p5 = new TreeNode(9);
		p6 = new TreeNode(5);
		p7 = new TreeNode(11);
		p8 = new TreeNode(4);
		
		root.left = p1;
		root.right = p2;
		
		p1.left = p3;
		p1.right = p4;
		
		p2.left = null;
		p2.right = p5;
		
		p3.left = null;
		p3.right = null;
				
		p4.left = p6;
		p4.right = p7;
		
		p5.left = p8;
	}
	
	private int ConvergeCastAlgo(TreeNode root) {
		if (root == null) return 0;
		int left = ConvergeCastAlgo(root.left);
		int right = ConvergeCastAlgo(root.right);
		return Math.max(root.val, Math.max(left, right)); //Eventually the root node gets the final max value of all nodes
	}
	
	private void helper(TreeNode root) {
		if (root == null) return;
		helper(root.left);
		helper(root.right);
		System.out.print(root.val+" ");
	}
}