package com.eivind.reversi.game;

public enum Color {
	BLACK(0), WHITE(1);
	private int color;
	private Color(int color){
		this.color = color;
	}
	
	public int color(){
		return this.color;
	}
}
