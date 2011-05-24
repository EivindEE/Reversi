package com.eivind.reversi.game;

import java.util.List;

public class ReversiGame {

	private ReversiState currentState;

	public ReversiGame(ReversiState startState){
		this.currentState = startState;
	}

	/**
	 * Returns true if the game is completed
	 * @return
	 */
	public boolean isGameComplete(){
		return currentState.isTerminal();
	}

	public List<ReversiState> possibleMoves(){
		return currentState.successors();
	}


	/**
	 * Returns the current state of the game
	 * @return
	 */
	public ReversiState currentState(){
		return this.currentState;
	}

	/**
	 * Changes the current state of the game, to one of the current states successors.
	 * @param state
	 */
	public void performeMove(ReversiState state){
		boolean stateChanged = false;
		for(ReversiState s : currentState.successors())
			if(state.equals(s)){
				this.currentState = s;
				stateChanged = true;
				break;
			}
		if(!stateChanged)
			throw new IllegalArgumentException("That state is not reachable from the current state");
	}

	public String toString(){
		return this.currentState.toString();
	}

	/**
	 * Should only be called if the game is completed
	 * Returns 0 if winner is Black
	 * Returns 1 if winner is White
	 * Returns -1 if game is Draw
	 * @return 
	 */
	public int winner(){
		if(isGameComplete()){
			int blackScore = currentState.getScore(Color.BLACK.color());
			int whiteScore = currentState.getScore(Color.WHITE.color());
			if( blackScore > whiteScore)
				return Color.BLACK.color();
			if(blackScore < whiteScore)
				return Color.WHITE.color();
			return -1;
		}
		throw new GameNotCompleteExeption("Asked for the winner before game was completed");
	}
}
