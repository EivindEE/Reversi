package com.eivind.reversi.controller.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.eivind.reversi.controller.events.MovePerformedEvent;
import com.eivind.reversi.controller.events.MoveRequestEvent;
import com.eivind.reversi.game.Color;
import com.eivind.reversi.game.Coordinate;
import com.eivind.reversi.game.ReversiGame;
import com.eivind.reversi.game.ReversiState;
import com.eivind.reversi.gui.Square;

public class GUIPlayer extends Player{
	public GUIPlayer(ReversiGame game, Color color) {
		super(game, color);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(game.currentState().player() == color.color()){
			if(e.getComponent() instanceof Square){
				Coordinate c = ((Square) e.getComponent()).getCoordinate();
				System.out.println(c);
				if(game.currentState().board().isEmpty(c)){
					for(ReversiState s: game.currentState().successors()){
						if(!s.board().isEmpty(c)){
							fireMoveEvent(new MovePerformedEvent(this, s));
							break;
						}
					}
				}
			}

		}

	}

	@Override
	public void moveRequestedEventOccured(MoveRequestEvent event) {

	}
}
