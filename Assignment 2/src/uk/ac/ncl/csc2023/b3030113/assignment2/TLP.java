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
		//Initialize trucks list
		trucks = new LinkedList<Truck>();
		piles = 0;
	}

	/**
	 * Creates array of random boxes to be allocated
	 * @param numberOfBoxes: number of boxes to be created
	 */
	public void makeBoxes(int numberOfBoxes){
		//Assign size of box array
		boxes = new Box[numberOfBoxes];
		for(int i=0;i<numberOfBoxes;i++){
			Random rand = new Random();
			//Make width of box random length between 1 and Truck width
			int width = rand.nextInt((Truck.TRUCK_WIDTH - MIN_BOX_SIZE) + 1) + MIN_BOX_SIZE;
			//Make height of box random length between 1 and Truck height
			int height = rand.nextInt((Truck.TRUCK_HEIGHT - MIN_BOX_SIZE) + 1) + MIN_BOX_SIZE;
			boxes[i] = new Box(width,height);
			System.out.println("Box "+i+" has W: "+boxes[i].getWidth()+" and H: "+boxes[i].getHeight());
		}
	}




	/**
	 * Method to solve Truck Loading Problem via Next-Fit On-Line strategy
	 */
	public void nftlp(){
		//Clear any previous truck list
		trucks.clear();
		//Add empty truck to list
		trucks.add(new Truck());
		piles = 0;
		System.out.println("");
		
		//Add empty pile to empty truck in beginning of truck list  
		Pile p = new Pile();
		//Make pile equal to first box to be added
		p.setCurrentWidth(boxes[0].getWidth());
		//Add pile to truck
		trucks.get(0).addPile(p);
		piles++;
		
		for(int i=0;i<boxes.length;i++){
			//Add boxes via NF approach
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

			//Check box is smaller or equal to the box below and fits in truck
			if(b.getWidth()<=p.getCurrentWidth() && p.getCurrentHeight()+b.getHeight()<=Truck.TRUCK_HEIGHT){
				//Box fits in pile
				p.addBox(b);
				t.increaseBoxNumber();
				System.out.println("Box added at Truck: "+trucks.indexOf(t)+" Pile: "+trucks.get(trucks.indexOf(t)).getPiles().indexOf(p));
			}
			else{
				//Create new pile
				Pile p2 = new Pile();
				//Check if new pile with box fits in truck
				if(t.getCurrentWidth()+b.getWidth()<=Truck.TRUCK_WIDTH && b.getHeight()<=Truck.TRUCK_HEIGHT){
					//Add box to new pile
					p2.addBox(b);
					//Add new pile to truck
					t.addPile(p2);
					piles++;
					t.increaseBoxNumber();
					System.out.println("Box added at Truck: "+trucks.indexOf(t)+" Pile: "+trucks.get(trucks.indexOf(t)).getPiles().indexOf(p2));
				}
				//If new pile does not fit in truck, make a new one with said pile
				else{
					//Create new truck
					Truck t2 = new Truck();
					//Add box to new pile
					p2.addBox(b);
					//Add pile in new truck
					t2.addPile(p2);
					piles++;
					//Increase number of boxes in truck
					t2.increaseBoxNumber();
					//Add truck to list
					trucks.add(t2);
					System.out.println("Box added at Truck: "+trucks.indexOf(t2)+" Pile: "+trucks.get(trucks.indexOf(t2)).getPiles().indexOf(p2));
				}
			}
		}
		else{
			//Create new truck
			Truck t2 = new Truck();
			//Make new pile
			Pile p2 = new Pile();
			//Add box to pile
			p2.addBox(b);
			//Add pile to new truck
			t2.addPile(p2);
			piles++;
			t2.increaseBoxNumber();
			//Add truck to list
			trucks.add(t2);
		}
	}












	/**
	 * Method to solve Truck Loading Problem via Best-Fit On-Line strategy
	 */
	public void bftlp(){
		//Clear any previous truck list
		trucks.clear();
		//Add empty truck to truck list
		trucks.add(new Truck());
		System.out.println("");
		piles = 0;
		
		for(int i=0;i<boxes.length;i++){
			//Add boxesvia BF approach
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

		//minimum amount of space used
		int minSpace = Truck.TRUCK_WIDTH;
		//Pile to add box to
		int minPile = 0;
		//Truck to add box to
		int minTruck = 0;
		//Determine if need to make a new pile
		boolean makePile = false;

		//Iterate through Trucks
		for(int i=0;i<trucks.size();i++){
			//Truck at current iteration in loop
			Truck t = trucks.get(i);

			//Check if any piles in truck
			//If truck empty add a pile with the size of the box
			if(t.getPiles().isEmpty()){
				Pile pile = new Pile();
				pile.setCurrentWidth(b.getWidth());
				t.addPile(pile);	
				piles++;
				//minimum space used equal to box width
				minSpace = b.getWidth();
			}

			//Check if box limit not reached
			if(t.getBoxNumber()<Truck.BOX_LIMIT){

				//Iterate through piles in truck i
				for(int j=0;j<t.getPiles().size();j++){
					//Pile at current position in Truck
					Pile p = t.getPiles().get(j);

					//Check if box is suitable for Pile j
					if(b.getWidth()<=p.getCurrentWidth() && p.getCurrentHeight()+b.getHeight()<=Truck.TRUCK_HEIGHT){
						//Check if space found is smallest one so far, and greater then zero
						if(p.getCurrentWidth()-b.getWidth()<minSpace && p.getCurrentWidth()-b.getWidth()>=0){
							//Set new minimal space
							minSpace = p.getCurrentWidth()-b.getWidth();
							//Set optimal pile to current one
							minPile = j;
							//Set optimal truck to current truck
							minTruck = i;
							//Set need to add new pile false
							makePile = false;
						}
					}
				}
				//Check if space in truck not occupied by a pile is better for box
				if(Truck.TRUCK_WIDTH-t.getCurrentWidth()-b.getWidth()<minSpace && (Truck.TRUCK_WIDTH-t.getCurrentWidth()-b.getWidth())>=0){
					//Minimum space used equal to space unused in truck
					minSpace = Truck.TRUCK_WIDTH-t.getCurrentWidth()-b.getWidth();
					//Truck to add box into 
					minTruck = i;
					//make new pile
					makePile = true;
				}
			}
		}
		//If box fits in an existing pile
		if(minSpace<=trucks.get(minTruck).getCurrentWidth()-b.getWidth() && makePile==false){
			trucks.get(minTruck).getPiles().get(minPile).addBox(b);
			trucks.get(minTruck).increaseBoxNumber();
		}
		//If box's ideal position needs to create a new pile
		else if(makePile==true && trucks.get(minTruck).getCurrentWidth()+b.getWidth()<=Truck.TRUCK_WIDTH){
			//Make new pile
			Pile p2 = new Pile();
			//Add box to new pile
			p2.addBox(b);
			//Add pile to truck
			trucks.get(minTruck).addPile(p2);
			piles++;
			trucks.get(minTruck).increaseBoxNumber();
			minPile = trucks.get(minTruck).getPiles().size()-1;
		}
		else{
			//Create new truck
			Truck t2 = new Truck();
			//Make new pile
			Pile p2 = new Pile();
			//Add box to pile
			p2.addBox(b);
			//Add pile to new truck
			t2.addPile(p2);
			piles++;
			t2.increaseBoxNumber();
			//Add truck to list
			trucks.add(t2);
			minTruck = trucks.size()-1;
			minPile = 0;
		}
		System.out.println("Box added at Truck: "+minTruck+" Pile: "+minPile);
	}





	/**
	 * Method to get Box array
	 * @return - Box Array
	 */
	public Box[] getBoxes(){
		return boxes;
	}

	/**
	 * Method to get Truck list
	 * @return - Truck list
	 */
	public List<Truck> getTrucks(){
		return trucks;
	}


}
