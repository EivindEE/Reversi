package com.eivind.reversi.game;

import java.util.ArrayList;

/**
 * Encapsulates a position on a gameboard via a Cartesian coordinate
 * 
 * @author Eivind Eidheim Elseth
 *
 */

public class Coordinate {
	private int x;
	private int y;

	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
	
	public String toString(){
		char alfa = 97;
		alfa += x;
		return "[" + alfa + "," + (y+1) + "]";
	}
	
	/**
	 * To Coordinates are considered equal if they occupy the same space
	 * @param c
	 * @return true if equal
	 */
	public boolean equals(Coordinate c){
		return c.x == this.x && c.y == this.y;
	}
	
	/**
	 * Assumes that all coordinates between this and c falls on valid coordinates.
	 * @param c
	 * @return
	 */
	public ArrayList<Coordinate> between(Coordinate c){
		ArrayList<Coordinate> between = new ArrayList<Coordinate>();
		
		// Horizontally
		if(this.x == c.x){
			int minY = Math.min(y, c.y) + 1;
			int maxY = Math.max(y, c.y);
			while(minY < maxY){
				between.add(new Coordinate(x, minY));
				minY++;
			}
		}
		// Vertically
		else if(this.y == c.y){
			int minX = Math.min(x, c.x) + 1;
			int maxX = Math.max(x, c.x);
			while(minX < maxX){
				between.add(new Coordinate(minX, y));
				minX++;
			}
		}
		// Diagonal up right
		else if(this.y < c.y && this.x < c.x){
			int xC = this.x + 1;
			int yC = this.y + 1;
			while(yC < c.y){
				between.add(new Coordinate(xC, yC));
				yC++;
				xC++;
			}
		}
		// Diagonal down right
		else if(this.y > c.y && this.x < c.x){
			int xC = this.x + 1;
			int yC = this.y - 1;
			while(yC > c.y){
				between.add(new Coordinate(xC, yC));
				yC--;
				xC++;
			}
		}
		// Diagonal up left
		else if(this.y < c.y && this.x > c.x){
			int xC = this.x - 1;
			int yC = this.y + 1;
			while(yC < c.y){
				between.add(new Coordinate(xC, yC));
				yC++;
				xC--;
			}
		}
		// Diagonal down left
		else if(this.y > c.y && this.x > c.x){
			int xC = this.x-1;
			int yC = this.y-1;
			while(yC > c.y){
				between.add(new Coordinate(xC, yC));
				yC--;
				xC--;
			} 
		}
		
		return between;
	}

}
