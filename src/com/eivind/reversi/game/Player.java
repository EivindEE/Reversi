package com.eivind.reversi.game;
/**
 * Abstract class describing a general player.
 * 
 * @author Eivind Eidheim Elseth
 *
 */
public abstract class Player {

	/**
	 * The color of the Players pieces
	 */
	private Color color;
	
	protected Player(Color identifier){
		this.color = identifier;
	}
	
	public int getColor(){
		return color.color();
	}
	
	/**
	 * Gets the Players next move 
	 * @return a Move with the players color and designated move
	 */
	public abstract Move getMove();
	
	@Override
	public abstract Player clone();
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName() + ", identifier: " + color;
	}
}
