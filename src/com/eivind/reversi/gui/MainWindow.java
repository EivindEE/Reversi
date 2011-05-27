package com.eivind.reversi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.eivind.reversi.game.ReversiGame;

public class MainWindow extends JFrame {
	private JMenuBar menuBar;
	private InfoPanel infoPanel;
	private GamePanel gamePanel;
	private ReversiGame game;
	
	public MainWindow(ReversiGame board){
		this.game = board;
		initComponents();
		packComponents();
		setSize(getSize());
		setResizable(false);
		setVisible(true);
	}

	private void initComponents() {
		menuBar = new JMenuBar();
		menuBar.add(new JMenu("File"));
		infoPanel = new InfoPanel();
		
		gamePanel = new GamePanel(game);
	}

	private void packComponents() {
		this.setJMenuBar(menuBar);
		this.add(infoPanel, BorderLayout.NORTH);
		this.add(gamePanel, BorderLayout.CENTER);
		
	}
	
	public Dimension getSize(){
		int width = Math.max(this.infoPanel.getWidth(), this.gamePanel.getWidth());
		int height = this.infoPanel.getHeight() + this.gamePanel.getWidth();
		return new Dimension(width, height);
	}
	public InfoPanel infoPanel(){
		return infoPanel;
	}
	
	public GamePanel gamePanel(){
		return gamePanel;
	}
	
}
