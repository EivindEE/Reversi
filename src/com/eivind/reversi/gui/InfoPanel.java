package com.eivind.reversi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String playerToMoveString = "Player to move: ";
	private final String blackScoreString = "Black score: ";
	private final String whiteScoreString = "White score: ";
	
	private JLabel toMove;
	private JLabel blackScore;
	private JLabel whiteScore;
	
	public InfoPanel(){
		super();
		setBackground(Color.LIGHT_GRAY);
		initLabels();
		pack();
	}

	private void initLabels() {
		toMove = new JLabel(playerToMoveString + "Black", JLabel.CENTER);
		blackScore = new JLabel(blackScoreString + "2", JLabel.CENTER);
		whiteScore = new JLabel(whiteScoreString + "2", JLabel.CENTER);	
	}
	
	private void pack() {
		
		add(blackScore, BorderLayout.WEST);
		add(toMove, BorderLayout.CENTER);
		add(whiteScore, BorderLayout.EAST);
	}


	
}
