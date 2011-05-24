package com.eivind.reversi.game;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a state in a reversi game.
 * Holds the player to move, the board, and possible successor states.
 * @author Eivind Eidheim Elseth
 *
 */
public class ReversiState {

	/**
	 * The index of the player to move
	 */
	private byte playerToMove;
	/**
	 * The reversi board
	 */
	private ReversiBoard board;
	/**
	 * List of legal states that can be reached from this state
	 */
	private List<ReversiState> successors;
	/**
	 * The calculated value of this state
	 */
	private short value;

	
	public ReversiState(byte player, ReversiBoard board){
		this.playerToMove = player;
		this.board = board;
	}
	
	public List<ReversiState> successors(){
		if(this.successors == null)
			initiateSuccessors();
		return successors;
	}
	
	public boolean isTerminal(){
		return board.isGameComplete();
	}
	
	public int getScore(int color){
		return board.getScore(color);
	}
	
	/**
	 * Returns the index of the player to move
	 * @return
	 */
	public int player(){
		return playerToMove;
	}
	
	/**
	 * Returns the index of the opponent of the player to move
	 * @return
	 */
	public int opponent(){
		return otherPlayer();
	}
	
	public int getValue(){
		return this.value;
	}

	private void initiateSuccessors() {
		successors = new LinkedList<ReversiState>();
		List<Coordinate> legalTiles= board.getLegalMoves(playerToMove);
		List<Move> legalMoves = new LinkedList<Move>();
		for(Coordinate c : legalTiles){
			legalMoves.add(new Move(playerToMove, c));
		}
		for(Move m: legalMoves){
			ReversiBoard successorBoard = new ReversiBitboard((ReversiBitboard) board, m);
			if(successorBoard.hasLegalMoves(otherPlayer())){
				successors.add(new ReversiState(otherPlayer(), successorBoard));
			}
			else{
				successors.add(new ReversiState(playerToMove, successorBoard));
			}
		}
	}
	
	
	private byte otherPlayer() {
		return (byte) (1 -playerToMove);
	}
	
	/**
	 * Returns a string representation of the state
	 */
	public String toString(){
		String playerToMoveString = "";
		if(playerToMove == 0 )
			playerToMoveString = "BLACK";
		if(playerToMove == 1 )
			playerToMoveString = "WHITE";
		return "Player to move" + playerToMoveString + '\n' + board; 
	}
	
	/**
	 * Two states are equal if they have equal boards, and the same player to move
	 */
	@Override
	public boolean equals(Object obj){
		if(obj instanceof ReversiState){
			ReversiState s = (ReversiState) obj;
			if(s.board.equals(board) && s.playerToMove == this.playerToMove)
				return true;
		}
		return false;
			
	}
}
