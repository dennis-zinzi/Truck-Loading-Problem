package uk.ac.ncl.csc2023.b3030113.assignment2;

import java.util.*;

public class Pile {

	private int currentWidth;
	private int currentHeight;

	private List<Box> boxes;

	/**
	 * Pile constructor
	 */
	public Pile(){
		boxes = new LinkedList<Box>();
	}

	/**
	 * Method to add box to pile
	 * @param b - box to add
	 */
	public void addBox(Box b){
		boxes.add(b);
		//Update current width to width of box
		currentWidth = b.getWidth();
		//Update current height
		currentHeight+=b.getHeight();
	}

	/**
	 * Method to get current width of pile
	 * @return - pile's current width
	 */
	public int getCurrentWidth() {
		return currentWidth;
	}

	/**
	 * Method to set current width of pile 
	 * @param currentWidth - Pile's new width 
	 */
	public void setCurrentWidth(int currentWidth) {
		this.currentWidth = currentWidth;
	}

	/**
	 * Method to get height of pile
	 * @return - pile's height
	 */
	public int getCurrentHeight() {
		return currentHeight;
	}

	/**
	 * Method to update Pile's height
	 * @param boxHeight - number to increase height by
	 */
	public void setCurrentHeight(int boxHeight) {
		currentHeight += boxHeight;
	}

	/**
	 * Method to get list of Boxes in Pile
	 * @return - Box list
	 */
	public List<Box> getBoxes() {
		return boxes;
	}



}
