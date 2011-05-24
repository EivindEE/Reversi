package com.eivind.reversi.game;

/**
 * This class encapsulates the idea of a move as consisting of the player making it, and the placement of the tile
 * A move is immutable
 * @author Eivind Eidheim Elseth
 *
 */
public class Move {
	/**
	 * Index of the player to move
	 */
	private int player;
	private Coordinate move;
	
	public Move(int player, Coordinate move){
		this.player = player;
		this.move = move;
	}
	
	
	public int player(){
		return player;
	}
	
	public Coordinate move(){
		return move;
	}
	
	public String toString(){
		String color = "";
		if(player == 0)
			color = "BLACK";
		if(player == 1)
			color = "WHITE";
		return "[" + color + ", " + move.toString() + "]";
	}

}
