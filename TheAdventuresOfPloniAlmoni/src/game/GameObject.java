package game;

import java.awt.Graphics;
import java.util.Arrays;

interface GameObject {
	public void paint(Graphics brush);
	
	default int[][] getGamePointsSeparateCoordinates(Point[] points) {
		int[] xArr = new int[points.length];
		int[] yArr = new int[points.length];
		for(int i = 0; i < points.length; i++) {
			xArr[i] = (int)points[i].getX();
			yArr[i] = (int)points[i].getY();
		}
		return new int[][] {xArr, yArr};
	}
	
	default void addOffset(int[][] arr) {
		//Lambda expressions
		Arrays.setAll(arr[0],i -> arr[0][i] + TheAdventuresOfPloniAlmoni.offsetx);
		Arrays.setAll(arr[1],i -> arr[1][i] + TheAdventuresOfPloniAlmoni.offsety);
	}
}