package uk.ac.ncl.csc2023.b3030113.assignment2;

import java.util.*;
import java.util.List;
import java.util.Random;

public class TLP {

	private Box[] boxes;
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

	/*
	 * 
	 * 
	 * NEED TO CHECK IF BOX SUITABLE FOR PILE!!!!!
	 * 
	 * 
	 */

	public void bftlpAddBox(Box b){
		int minSpace = Truck.TRUCK_WIDTH;
		int minPile = 0;
		int minTruck = 0;
		boolean makePile = false;

		//Iterate through Trucks
		for(int i=0;i<trucks.size();i++){
			//Check if box limit not reached
			if(trucks.get(i).getBoxNumber()<Truck.BOX_LIMIT){
				//Iterate through piles in truck i
				for(int j=0;j<trucks.get(i).getPiles().size();j++){
					//Check if box is suitable for Pile j
					if(b.getWidth()<=trucks.get(i).getPiles().get(j).getCurrentWidth() && trucks.get(i).getPiles().get(j).getCurrentHeight()+b.getHeight()<=Truck.TRUCK_HEIGHT){
						//If space unused on Pile j after box would be added is lower than previous one update minSpace details
						if(trucks.get(i).getPiles().get(j).getCurrentWidth()-b.getWidth()<=minSpace){
							minSpace = trucks.get(i).getPiles().get(j).getCurrentWidth()-b.getWidth();
							minPile = j;
							minTruck = i;
							makePile = false;
						}
					}
				}
				if(Truck.TRUCK_WIDTH-trucks.get(i).getCurrentWidth()<minSpace){
					minSpace = Truck.TRUCK_WIDTH-trucks.get(i).getCurrentWidth();
					minTruck = i;
					makePile = true;
				}
			}
		}
		if(minSpace<=b.getWidth() && makePile==false){
			System.out.println("Add box normally");
			trucks.get(minTruck).getPiles().get(minPile).addBox(b);
		}
		else if(minSpace<=b.getWidth() && makePile==true){
			System.out.println("New pile in truck");
			Pile p = new Pile();
			p.addBox(b);
			trucks.get(minTruck).addPile(p);
			piles++;
			trucks.get(minTruck).increaseBoxNumber();
			trucks.get(minTruck).setCurrentWidth(p.getCurrentWidth());
		}
		else{
			System.out.println("New truck");
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
		System.out.println("");
		for(int i=0;i<boxes.length;i++){
			bftlpAddBox(boxes[i]);
		}
		//See how many trucks used
		System.out.println("Trucks used: "+trucks.size());
		System.out.println("Piles used: "+piles);
	}

}
