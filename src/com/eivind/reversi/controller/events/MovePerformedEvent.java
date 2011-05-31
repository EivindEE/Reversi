package com.eivind.reversi.controller.events;

import java.util.EventObject;

import com.eivind.reversi.game.ReversiGame;
import com.eivind.reversi.game.ReversiState;

public class MovePerformedEvent extends EventObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReversiState state;

	public MovePerformedEvent(Object source) {
		super(source);
	}
	
	public MovePerformedEvent(Object source, ReversiState s){
		this(source);
		this.state = s;
	}
	
	public ReversiState state(){
		return state;
	}

}
