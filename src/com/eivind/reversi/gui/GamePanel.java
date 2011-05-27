package com.eivind.reversi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.eivind.reversi.game.ReversiGame;

public class GamePanel extends JPanel {
	private static int COLUMNS = 8;
	private static int ROWS = 8;
	private static int CELLSIZE = 70;
	private static int WIDTH = CELLSIZE * COLUMNS;
	private static int HEIGHT = CELLSIZE * ROWS;
	
	private static ImageIcon blackPiece = new ImageIcon("images/blackPiece.png");
	private static ImageIcon whitePiece = new ImageIcon("images/whitePiece.png");
	private Dimension dimension;
	private Square[][] squares;
	private ReversiGame game;
	
	public GamePanel(ReversiGame game){
		this.game = game;
		this.dimension = new Dimension(WIDTH,HEIGHT);
		this.setSize(dimension);
		setBackground(Color.GREEN);
		setLayout(new GridLayout(COLUMNS, ROWS));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		initSquares();
		updateBoard();
	}
	
	private void initSquares() {
		squares = new Square[ROWS][COLUMNS];
		for(int i = ROWS-1; i >= 0; i--){
			for (int j = 0; j < COLUMNS; j++) {
				squares[i][j] = new Square(CELLSIZE, j, i);
				this.add(squares[i][j]);
			}
		}
	}
	
	

	public Dimension getSize(){
		return dimension;
	}
	
	public void updateBoard(){
		for(int i = 0; i < squares.length; i++)
			for (int j = 0; j < squares[0].length; j++) {
				int color = game.currentState().board().getTile(squares[i][j].getCoordinate());
				if(color == 0)
					squares[i][j].setPiece(blackPiece);
				if(color == 1)
					squares[i][j].setPiece(whitePiece);
				if(color == -1)
					squares[i][j].setPiece(null);
			}
	}
	
	@Override
	public void addMouseListener(MouseListener l){
		for(int i = 0; i < squares.length; i++)
			for (int j = 0; j < squares[0].length; j++)
				squares[i][j].addMouseListener(l);
	}
	
	public String toString(){
		return this.getClass().toString();
	}
	
}
