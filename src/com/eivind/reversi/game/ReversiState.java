package com.eivind.reversi.game;

import java.util.LinkedList;
import java.util.List;

/**
 * Class
 * @author Eivind Eidheim Elseth
 *
 */
public class ReversiState {
	/**
	 * The players playing the game
	 */
	private Player[] players;
	/**
	 * The index of the player to move
	 */
	private int playerToMove;
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
	private int value;
	/**
	 * Valuator that 
	 */
	private Valuation valuator;
	
	public ReversiState(Player[] players, int player, ReversiBoard board){
		this.players = players;
		this.playerToMove = player;
		this.board = board;
	}
	
	public List<ReversiState> successors(){
		if(this.successors == null)
			initiateSuccessors();
		return successors;
	}
	
	public boolean isTerminalState(){
		return board.isGameComplete();
	}
	
	public int getScore(Player player){
		return board.getScore(player.getColor());
	}
	
	/**
	 * Returns the player to move
	 * @return
	 */
	public Player player(){
		return players[playerToMove];
	}
	
	/**
	 * Returns the opponent of the player to move
	 * @return
	 */
	public Player opponent(){
		return players[otherPlayer()];
	}
	
	public int getUtility(){
		if(isTerminalState()){
			this.value = valuator.boardValue(this);
			return this.value;
		}
		throw new RuntimeException("getUtility called on a non-terminal node");
	}
	
	public int getValue(){
		return this.value;
	}

	private void initiateSuccessors() {
		successors = new LinkedList<ReversiState>();
		List<Coordinate> legalTiles= board.getLegalMoves(playerToMove);
		List<Move> legalMoves = new LinkedList<Move>();
		for(Coordinate c : legalTiles){
			legalMoves.add(new Move(players[playerToMove], c));
		}
		for(Move m: legalMoves){
			ReversiBoard successorBoard = new ReversiBitboard((ReversiBitboard) board, m);
			if(successorBoard.hasLegalMoves(otherPlayer())){
				successors.add(new ReversiState(players, otherPlayer(), successorBoard));
			}
			else{
				successors.add(new ReversiState(players, playerToMove, successorBoard));
			}
		}
	}
	
	
	private int otherPlayer() {
		return 1-playerToMove;
	}
	
	/**
	 * Returns a string representation of the state
	 */
	public String toString(){
		return "Player to move" + players[playerToMove] + '\n' + board; 
	}
	
	
	public static void main(String[] args) {
		Player[] p = new Player[]{new RandomPlayer(Color.BLACK), new RandomPlayer(Color.WHITE)};
		ReversiState state = new ReversiState(p, 0, new ReversiBitboard());
		int expandedStates = 0;
		for(ReversiState s : state.successors())
			for(ReversiState ss : s.successors())
				for(ReversiState sss : ss.successors())
					for(ReversiState ssss : sss.successors())
						for(ReversiState sssss : ssss.successors())
							for(ReversiState ssssss : sssss.successors())
								for(ReversiState sssssss : ssssss.successors()){
									if(expandedStates % 10000 == 0)
										System.out.println(expandedStates);
									for(ReversiState ssssssss : sssssss.successors())
//										for(ReversiState sssssssss : ssssssss.successors())
											expandedStates++;
								}
		System.out.println(expandedStates);
	}
}
