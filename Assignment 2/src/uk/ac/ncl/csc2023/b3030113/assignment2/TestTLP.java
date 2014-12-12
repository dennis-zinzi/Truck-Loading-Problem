package uk.ac.ncl.csc2023.b3030113.assignment2;

public class TestTLP {

	public static void main(String[] args) {
		TLP tlp = new TLP();
		//Choose amount of boxes to make
		int boxNumber = 100;
		//Create boxes
		tlp.makeBoxes(boxNumber);
		//Print out box details
		for(Box b: tlp.getBoxes()){
			System.out.println("Width: "+b.getWidth()+" Height: "+b.getHeight());
		}
		//Get current time before next-fit applied
		long t1 = System.nanoTime();
		//Apply next-fit to boxes
		tlp.nftlp();
		//Get current time after next-fit applied
		long t2 = System.nanoTime();
		//Time taken by next-fit
		System.out.println("Next-Fit time taken = "+(t2-t1)+"ns");
		//Get current time before best-fit applied
		t1 = System.nanoTime();
		//Apply best-fit to boxes
		tlp.bftlp();
		//Get current time after best-fit applied
		t2 = System.nanoTime();
		System.out.println("Best-Fit time taken "+"for "+boxNumber+" boxes = "+(t2-t1)+"ns");
	}
}

