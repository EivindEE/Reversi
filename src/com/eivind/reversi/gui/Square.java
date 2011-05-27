package com.eivind.reversi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageProducer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

import com.eivind.reversi.game.Coordinate;



public class Square extends JPanel {
	private JLabel tile;
	private Coordinate coordinate;
	public Square(int size, int x, int y){
		super();
		this.setSize(size, size);
		this.setCoordinate(new Coordinate(x, y));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLUE);
		this.tile = new JLabel();
		this.add(tile, BorderLayout.CENTER);
	}
	
	public void setPiece(ImageIcon piece){
		this.tile.setIcon(piece);
	}

	private void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	public String toString(){
		return this.coordinate.toString();
	}

}
