package com.eivind.reversi.game;

import java.util.List;

public interface ReversiBoard {

	public final static int BLACK = 0;
	public final static int EMPTY = -1;
	public final static int WHITE = 1;
	
	public final static int NUMBER_OF_ROWS = 8;
	public final static int NUMBER_OF_COLUMNS = 8;

	/**
	 * Returns the score of the player with the given color
	 * Should be called with one of the class constants.
	 * @param color
	 * @return
	 */
	public int getScore(int color);

	/**
	 * Return the int correspondig to the class constant of the content of the tile
	 * Should be used in conjunction with the class constants
	 * @param c
	 * @return
	 */
	public int getTile(Coordinate c);

	/**
	 * Returns true if the player with the given index has legal moves
	 * Should be called with either the static BLACK or WHITE
	 * @param color
	 * @return
	 */
	public boolean hasLegalMoves(int color);

	/**
	 * Returns true if the tile at c is empty
	 * @param c
	 * @return
	 */
	public boolean isEmpty(Coordinate c);

	/**
	 * Returns true if none of the players have legal moves
	 * @return
	 */
	public boolean isGameComplete();

	/**
	 * Returns a list of all the coordinates the given player can legally place a piece 
	 * 
	 * @param player
	 * @return
	 */
	public List<Coordinate> getLegalMoves(int player);
	
	/**
	 * Returns a string representation of the ReversiBoard
	 * @return
	 */
	public String toString();

}