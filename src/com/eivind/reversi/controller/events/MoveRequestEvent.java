package com.eivind.reversi.controller.events;

import java.util.EventObject;

import com.eivind.reversi.game.ReversiState;

public class MoveRequestEvent extends EventObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReversiState state;

	public MoveRequestEvent(Object source) {
		super(source);
	}
	
	public MoveRequestEvent(Object source, ReversiState s){
		this(source);
		this.state = s;
	}
	
	public ReversiState state(){
		return state;
	}

}
