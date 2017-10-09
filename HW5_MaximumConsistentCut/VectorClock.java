package HW5_MaximumConsistentCut;


public class VectorClock implements Comparable<VectorClock>{
	int[] vc;

	public VectorClock( int noOfProcesses ) {
		vc = new int [noOfProcesses];
		
	}
	
	public VectorClock(int vc[]) {
	  this.vc=vc;
	}
		 

	@Override
	public int compareTo(VectorClock o) {
	  if(this.vc[0]>=o.vc[0] && this.vc[1]>=o.vc[1]) {
      return 1;
    } else if (this.vc[0]<=o.vc[0] && this.vc[1]<=o.vc[1] ) {
      return -1;
    }else if (this.vc[0]==o.vc[0] && this.vc[1]==o.vc[1] ) {
      return 0;
    } else {
      return 2;
    }
	}
	/**
	 * Based on a event vector clock will be incremented, changed or updated.
	 * Which index should be updated will be decided by a processor
	 * @param index
	 * @param value
	 */
	public void updateAt(int index, int value){
		vc[index]= value;
	}
	
	public String toString() {
	  return "[" +vc[0]+","+vc[1]+ "]";
	}
	
	
}
