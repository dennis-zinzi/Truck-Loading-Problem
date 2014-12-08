package uk.ac.ncl.csc2023.b3030113.assignment2;

import java.util.*;

public class Pile {

	private int currentWidth;
	private int currentHeight;

	private List<Box> boxes;

	public Pile(){
		boxes = new LinkedList<Box>();
	}

	public void addBox(Box b){
		boxes.add(b);
		currentWidth = b.getWidth();
		currentHeight+=b.getHeight();
	}

	public int getCurrentWidth() {
		return currentWidth;
	}

	public void setCurrentWidth(int currentWidth) {
		this.currentWidth = currentWidth;
	}

	public int getCurrentHeight() {
		return currentHeight;
	}

	public void setCurrentHeight(int boxHeight) {
		currentHeight += boxHeight;
	}

	public List<Box> getBoxes() {
		return boxes;
	}



}
