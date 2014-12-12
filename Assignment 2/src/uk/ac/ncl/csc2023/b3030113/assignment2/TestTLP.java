package uk.ac.ncl.csc2023.b3030113.assignment2;

public class TestTLP {

	public static void main(String[] args) {
		TLP tlp = new TLP();
		int boxNumber = 100000;
		tlp.makeBoxes(boxNumber);
		long t1 = System.nanoTime();
		tlp.nftlp();
		long t2 = System.nanoTime();
		System.out.println("Next-Fit time taken = "+(t2-t1)+"ns");
		t1 = System.nanoTime();
		tlp.bftlp();
		t2 = System.nanoTime();
		System.out.println("Best-Fit time taken "+"for "+boxNumber+" boxes = "+(t2-t1)+"ns");
	}
}
//For 10 boxes NF 3376000ns
//For 10 boxes BF 27923000ns

/* 
 * Trucks used: 564
 * Piles used: 774
 * Next-Fit time taken = 66312000ns
 */

/*
 * Trucks used: 696
 * Piles used: 512
 * Best-Fit time taken for 1000 boxes = 328312000ns
 */

/*
 * Trucks used: 61523
 * Piles used: 83647
 * Next-Fit time taken for 100000 boxes = 262094000ns
 */
