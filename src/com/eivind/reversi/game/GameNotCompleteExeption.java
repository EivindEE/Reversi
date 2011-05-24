package com.eivind.reversi.game;

public class GameNotCompleteExeption extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7551724296742872137L;
	
	public GameNotCompleteExeption(){
		super();
	}
	
	public GameNotCompleteExeption(String errorString){
		super(errorString);
	}

}
