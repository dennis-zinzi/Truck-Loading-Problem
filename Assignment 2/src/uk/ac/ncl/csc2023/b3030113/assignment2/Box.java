package uk.ac.ncl.csc2023.b3030113.assignment2;

public class Box {
	
	private int width;
	private int height;
	
	/**
	 * Box Constructor
	 * @param width - width of Box
	 * @param height - height of Box
	 */
	public Box(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Method to get box width
	 * @return - box width
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Method to get box height
	 * @return - box height
	 */
	public int getHeight(){
		return height;
	}
}
