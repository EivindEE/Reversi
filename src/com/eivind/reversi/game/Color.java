package com.eivind.reversi.game;

public enum Color {
	BLACK((byte)0), WHITE((byte)1);
	private byte color;
	private Color(byte color){
		this.color = color;
	}
	
	public byte color(){
		return this.color;
	}
}
