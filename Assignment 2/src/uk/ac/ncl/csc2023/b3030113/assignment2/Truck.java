package uk.ac.ncl.csc2023.b3030113.assignment2;

import java.util.*;

public class Truck {
	private final int width;
	private final int height;

	private int currentWidth;

	private int boxNumber;
	private List<Pile> piles;

	public final static int TRUCK_HEIGHT = 400;
	public final static int TRUCK_WIDTH = 500;
	
	public final static int BOX_LIMIT = 25;

	/**
	 * Truck constructor
	 */
	public Truck(){
		width = TRUCK_WIDTH;
		height = TRUCK_HEIGHT;
		currentWidth = 0;
		boxNumber = 0;
		piles = new LinkedList<Pile>();
	}

	/**
	 * Method to get current width used in Truck
	 * @return - current width used
	 */
	public int getCurrentWidth(){
		return currentWidth;
	}

	/**
	 * Method to increase the box count in truck
	 */
	public void increaseBoxNumber(){
		boxNumber++;
	}

	/**
	 * Method to get box number in truck 
	 * @return - box number in truck
	 */
	public int getBoxNumber(){
		return boxNumber;
	}

	/**
	 * Method to get Pile list
	 * @return - Pile list
	 */
	public List<Pile> getPiles(){
		return piles;
	}

	/**
	 * Method to add Pile to Truck
	 * @param p - Pile to add
	 */
	public void addPile(Pile p){
		piles.add(p);
		//Increase current width used by width of Pile p
		currentWidth += p.getCurrentWidth();
	}





}
