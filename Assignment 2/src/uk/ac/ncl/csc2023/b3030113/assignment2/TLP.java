package uk.ac.ncl.csc2023.b3030113.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TLP {

	private Box[] boxes;
	private List<Truck> trucks = new ArrayList<Truck>();

	private final static int TRUCK_HEIGHT = 400;
	private final static int TRUCK_WIDTH = 500;

	private final static int MIN_BOX_SIZE = 1;

	public TLP(){
		trucks.add(new Truck());
	}

	/**
	 * Creates array of random boxes to be allocated
	 * @param numberOfBoxes: number of boxes to be created
	 */
	public void makeBoxes(int numberOfBoxes){
		boxes = new Box[numberOfBoxes];
		for(int i=0;i<numberOfBoxes;i++){
			Random rand = new Random();
			int width = rand.nextInt((TRUCK_WIDTH - MIN_BOX_SIZE) + 1) + MIN_BOX_SIZE;
			int height = rand.nextInt((TRUCK_HEIGHT - MIN_BOX_SIZE) + 1) + MIN_BOX_SIZE;
			boxes[i] = new Box(width,height);
			System.out.println("Box "+i+" has W: "+boxes[i].getWidth()+" and H: "+boxes[i].getHeight());
		}
	}




	/**
	 * Method to solve Truck Loading Problem via Next-Fit On-Line strategy
	 */
	public void nftlp(){
		System.out.println("");
		for(int i=0;i<boxes.length;i++){
			nftlpAddBox(boxes[i], trucks.get(trucks.size()-1).getPile().get(trucks.get(trucks.size()-1).getPile().size()-1), trucks.get(trucks.size()-1) );
		}
		//See how many trucks used
		System.out.println("Trucks used: "+trucks.size());
	}


	/**
	 * Method to add Box to Truck via NFTLP approach
	 * @param b - Box to add
	 * @param p - Pile box will be added on
	 * @param t - Truck Box will be added on
	 */
	public void nftlpAddBox(Box b, Pile p, Truck t){
		//Check if number of boxes exceeded number allowed per truck
		if(t.getBoxNumber()<Truck.BOX_LIMIT){

			System.out.println("Within box number");

			System.out.println("Current pile width is: "+p.getCurrentWidth());
			System.out.println("Current height is: "+p.getCurrentHeight());
			System.out.println("Box width is: "+b.getWidth());
			System.out.println("Box height is: "+b.getHeight());


			//Check box is smaller or equal to the box below and fits in truck
			if(b.getWidth()<=p.getCurrentWidth() && p.getCurrentHeight()+b.getHeight()<=Truck.TRUCK_HEIGHT){
				//Box fits in pile
				p.addBox(b);
				t.increaseBoxNumber();
			}
			else{
				System.out.println("New pile must be made");
				//Create new pile
				Pile p2 = new Pile();
				//Check if new pile with box fits in truck
				if(t.getCurrentWidth()+b.getWidth()<=TRUCK_WIDTH && b.getHeight()<=Truck.TRUCK_HEIGHT){
					System.out.println("Add box in new pile in truck");
					//Add new pile to truck
					t.addPile(p2);
					//Add box to new pile
					p2.addBox(b);
					t.increaseBoxNumber();
					t.setCurrentWidth(p2.getCurrentWidth());
				}
				//If new pile does not fit in truck, make a new one with said pile
				else{
					System.out.println("Add new truck for box");
					//Create new truck
					Truck t2 = new Truck();
					//Add truck to list
					trucks.add(t2);
					//Add pile in new truck
					t2.addPile(p2);
					//Add box to new pile
					p2.addBox(b);
					//Increase number of boxes in truck
					t2.increaseBoxNumber();
					//Update current width used in truck
					t2.setCurrentWidth(p2.getCurrentWidth());
				}
			}
		}
		else{
			System.out.println("New truck needed");
			//Create new truck
			Truck t2 = new Truck();
			//Add truck to list
			trucks.add(t2);
			//Make new pile
			Pile p2 = new Pile();
			//Add pile to new truck
			t2.addPile(p2);
			//Add box to pile
			p2.addBox(b);
			t2.increaseBoxNumber();
		}
		System.out.println("Box added");
		System.out.println("");
	}


	public void bftlpAddBox(Box b){
		
		for(int i=0;i<trucks.size();i++){
			if(trucks.get(i).getBoxNumber()<Truck.BOX_LIMIT){
				for(int j=0;j<trucks.get(i).getPile().size();j++){

				}
			}
		}



	}






	public Box[] getBoxes(){
		return boxes;
	}

	public List<Truck> getTrucks(){
		return trucks;
	}

	/**
	 * Method to solve Truck Loading Problem via Best-Fit On-Line strategy
	 */
	public void bftlp(){

	}

}