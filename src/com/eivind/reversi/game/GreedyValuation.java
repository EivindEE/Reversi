package com.eivind.reversi.game;

public class GreedyValuation implements Valuation {

	@Override
	public int boardValue(ReversiState state) {
		int playerScore = state.getScore(state.player());
		int opponentScore = state.getScore(state.opponent());

		return playerScore - opponentScore;
	}

}
