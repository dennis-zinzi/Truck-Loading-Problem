package uk.ac.ncl.csc2023.b3030113.assignment2;

import java.util.*;
import java.util.List;
import java.util.Random;

public class TLP {

	private Box[] boxes = {new Box(467,113),new Box(315,256),new Box(484,312),new Box(140,274),
			new Box(373,380),new Box(169,126),new Box(43,336),new Box(103,160),new Box(49,93),new Box(21,139)};
	private List<Truck> trucks;
	private int piles;


	private final static int MIN_BOX_SIZE = 1;

	public TLP(){
		trucks = new LinkedList<Truck>();
		trucks.add(new Truck());
		piles = 0;
	}

	/**
	 * Creates array of random boxes to be allocated
	 * @param numberOfBoxes: number of boxes to be created
	 */
	public void makeBoxes(int numberOfBoxes){
		boxes = new Box[numberOfBoxes];
		for(int i=0;i<numberOfBoxes;i++){
			Random rand = new Random();
			int width = rand.nextInt((Truck.TRUCK_WIDTH - MIN_BOX_SIZE) + 1) + MIN_BOX_SIZE;
			int height = rand.nextInt((Truck.TRUCK_HEIGHT - MIN_BOX_SIZE) + 1) + MIN_BOX_SIZE;
			boxes[i] = new Box(width,height);
			System.out.println("Box "+i+" has W: "+boxes[i].getWidth()+" and H: "+boxes[i].getHeight());
		}
	}




	/**
	 * Method to solve Truck Loading Problem via Next-Fit On-Line strategy
	 */
	public void nftlp(){
		System.out.println("");
		Pile p = new Pile();
		p.setCurrentWidth(boxes[0].getWidth());
		trucks.get(0).addPile(p);
		for(int i=0;i<boxes.length;i++){
			nftlpAddBox(boxes[i], trucks.get(trucks.size()-1).getPiles().get(trucks.get(trucks.size()-1).getPiles().size()-1), trucks.get(trucks.size()-1) );
		}
		//See how many trucks used
		System.out.println("Trucks used: "+trucks.size());
		System.out.println("Piles used: "+piles);
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
				if(t.getCurrentWidth()+b.getWidth()<=Truck.TRUCK_WIDTH && b.getHeight()<=Truck.TRUCK_HEIGHT){
					System.out.println("Add box in new pile in truck");
					//Add box to new pile
					p2.addBox(b);
					//Add new pile to truck
					t.addPile(p2);
					piles++;
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
					//Add box to new pile
					p2.addBox(b);
					//Add pile in new truck
					t2.addPile(p2);
					piles++;
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
			//Add box to pile
			p2.addBox(b);
			//Add pile to new truck
			t2.addPile(p2);
			piles++;
			t2.increaseBoxNumber();
			t2.setCurrentWidth(p2.getCurrentWidth());
		}
		System.out.println("Box added");
		System.out.println("");
	}

	
	
	/**
	 * Method to solve Truck Loading Problem via Best-Fit On-Line strategy
	 */
	public void bftlp(){
		System.out.println("");
		
		for(int i=0;i<boxes.length;i++){
			bftlpAddBox(boxes[i]);
		}
		//See how many trucks used
		System.out.println("Trucks used: "+trucks.size());
		System.out.println("Piles used: "+piles);
	}


	/**
	 * Method to add a box via the Best-Fit On-Line Truck Loading Problem Approach
	 * @param b - Box to be added
	 */
	public void bftlpAddBox(Box b){
		int minSpace = Truck.TRUCK_WIDTH;
		int minPile = 0;
		int minTruck = 0;
		boolean makePile = false;
		//Iterate through Trucks
		for(int i=0;i<trucks.size();i++){
			//Truck at current iteration in loop
			Truck t = trucks.get(i);
			
			if(t.getPiles().isEmpty()){
				Pile pile = new Pile();
				pile.setCurrentWidth(b.getWidth());
				t.addPile(pile);			
			}
			
			//Check if box limit not reached
			if(t.getBoxNumber()<Truck.BOX_LIMIT){
				System.out.println("Piles: "+t.getPiles().size());
				System.out.println("current width: "+t.getCurrentWidth());
				
				//Iterate through piles in truck i
				for(int j=0;j<t.getPiles().size();j++){
					//Pile at current position in Truck
					Pile p = t.getPiles().get(j);
					System.out.println("Pile width: "+p.getCurrentWidth());
					
					//Check if box is suitable for Pile j
					if(b.getWidth()<=p.getCurrentWidth() && p.getCurrentHeight()+b.getHeight()<=Truck.TRUCK_HEIGHT){
						//Check if space found is smallest one so far, and greater then zero
						if(p.getCurrentWidth()-b.getWidth()<minSpace && p.getCurrentWidth()-b.getWidth()>=0){
							//Set new minimal space
							minSpace = p.getCurrentWidth()-b.getWidth();
							System.out.println("New minSpace: "+minSpace);
							//Set optimal pile to current one
							minPile = j;
							//Set optimal truck to current truck
							minTruck = i;
							//Set need to add new pile false
							makePile = false;
						}
					}
					System.out.println("Current minSpace: "+minSpace);
				}
				System.out.println("Current width in truck: "+t.getCurrentWidth());
				
				if(Truck.TRUCK_WIDTH-t.getCurrentWidth()<minSpace){// && Truck.TRUCK_WIDTH-t.getCurrentWidth()>=0){
					System.out.println(Truck.TRUCK_WIDTH-t.getCurrentWidth());
					minSpace = Truck.TRUCK_WIDTH-t.getCurrentWidth()-b.getWidth();
					System.out.println("NNew minSpace: "+minSpace);
					minTruck = i;
					makePile = true;
				}
				System.out.println("Truck minspace: "+minSpace);
				System.out.println(makePile);
			}
			System.out.println("Final: "+minSpace);
			System.out.println("Box size: "+b.getWidth());
			System.out.println(minSpace<=b.getWidth());
			System.out.println("");
		}
		if(minSpace<=b.getWidth() && makePile==false){
			System.out.println("Fits in an existing pile");
			trucks.get(minTruck).getPiles().get(minPile).addBox(b);
			trucks.get(minTruck).increaseBoxNumber();
		}
		else if(minSpace<=b.getWidth() && makePile==true){
			System.out.println("Needs new Pile");
			Pile p2 = new Pile();
			p2.addBox(b);
			trucks.get(minTruck).addPile(p2);
			piles++;
			trucks.get(minTruck).increaseBoxNumber();
			trucks.get(minTruck).setCurrentWidth(p2.getCurrentWidth());
			minPile = trucks.get(minTruck).getPiles().size()-1;
		}
		else{
			System.out.println("Needs new Truck");
			//Create new truck
			Truck t2 = new Truck();
			//Add truck to list
			trucks.add(t2);
			//Make new pile
			Pile p2 = new Pile();
			//Add box to pile
			p2.addBox(b);
			//Add pile to new truck
			t2.addPile(p2);
			piles++;
			t2.increaseBoxNumber();
			t2.setCurrentWidth(p2.getCurrentWidth());
			minTruck = trucks.size()-1;
			minPile = 0;
		}
		System.out.println("Box added in "+"truck: "+minTruck+" and pile: "+minPile);
		System.out.println("");
		System.out.println("");

	}






	public Box[] getBoxes(){
		return boxes;
	}

	public List<Truck> getTrucks(){
		return trucks;
	}


}
