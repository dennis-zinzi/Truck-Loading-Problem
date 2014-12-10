package uk.ac.ncl.csc2023.b3030113.assignment2;

public class TestTLP {

	public static void main(String[] args) {
		TLP tlp = new TLP();
		int boxNumber = 10;
		//tlp.makeBoxes(boxNumber);
		long t1 = System.nanoTime();
		//tlp.nftlp();
		long t2 = System.nanoTime();
		System.out.println("Next-Fit time taken = "+(t2-t1)+"ns");
		
		t1 = System.nanoTime();
		tlp.bftlp();
		t2 = System.nanoTime();
		System.out.println("Best-Fit time taken "+"for "+boxNumber+" boxes = "+(t2-t1)+"ns");
	}
}
