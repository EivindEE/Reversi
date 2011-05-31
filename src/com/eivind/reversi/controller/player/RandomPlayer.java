package com.eivind.reversi.controller.player;

import java.util.Random;

import com.eivind.reversi.controller.events.MovePerformedEvent;
import com.eivind.reversi.controller.events.MoveRequestEvent;

import com.eivind.reversi.game.Color;
import com.eivind.reversi.game.ReversiGame;
import com.eivind.reversi.game.ReversiState;

public class RandomPlayer extends Player {
	Random r;
	public RandomPlayer(ReversiGame game, Color color) {
		super(game, color);
		r = new Random();
	}

	@Override
	public void moveRequestedEventOccured(MoveRequestEvent event) {
		if(event.state().player() == this.color.color()){
			ReversiState currentState = this.game.currentState();
			int numberOfPossibleMoves = currentState.successors().size();
			ReversiState selectedMove = currentState.successors().get(r.nextInt(numberOfPossibleMoves));
			MovePerformedEvent move = new MovePerformedEvent(this, selectedMove);
			
			fireMoveEvent(move);
		}
	}
}
