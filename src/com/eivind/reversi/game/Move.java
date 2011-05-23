package com.eivind.reversi.game;

/**
 * This class encapsulates the idea of a move as consisting of the player making it, and the placement of the tile
 * A move is immutable
 * @author Eivind Eidheim Elseth
 *
 */
public class Move {
	private Player player;
	private Coordinate move;
	
	public Move(Player player, Coordinate move){
		this.player = player;
		this.move = move;
	}
	
	public Player player(){
		return player;
	}
	
	public Coordinate move(){
		return move;
	}

}
