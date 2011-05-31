package com.eivind.reversi.controller;


import com.eivind.reversi.controller.events.MovePerformedEvent;
import com.eivind.reversi.controller.events.MovePerformedEventListener;
import com.eivind.reversi.controller.events.MoveRequestEvent;
import com.eivind.reversi.controller.events.MoveRequestEventListener;

import com.eivind.reversi.controller.player.GUIPlayer;
import com.eivind.reversi.controller.player.Player;
import com.eivind.reversi.controller.player.RandomPlayer;
import com.eivind.reversi.game.Color;
import com.eivind.reversi.game.ReversiBitboard;
import com.eivind.reversi.game.ReversiGame;
import com.eivind.reversi.game.ReversiState;
import com.eivind.reversi.gui.MainWindow;


public class MainController implements MovePerformedEventListener{
	private ReversiGame game;
	private MainWindow window;
	private Player[] players;

	public MainController(ReversiGame game){
		this.game = game;
		this.window = new MainWindow(game);
		this.players = new Player[]{new GUIPlayer(game, Color.BLACK), new RandomPlayer(game, Color.WHITE)}; 
		this.window.gamePanel().addMouseListener(players[0]);
		this.window.gamePanel().addMouseListener(players[1]);
		players[0].addMoveEventListener(this);
		players[1].addMoveEventListener(this);
		this.addMoveRequestListener(players[0]);
		this.addMoveRequestListener(players[1]);
	}




	public static void main(String[] args){
		new MainController(new ReversiGame(new ReversiState(Color.BLACK.color(), new ReversiBitboard())));
	}
	@Override
	public void moveEventOccured(MovePerformedEvent event) {
		game.performeMove(event.state());
		window.gamePanel().updateBoard();
		window.repaint();
		MoveRequestEvent req = new MoveRequestEvent(this, this.game.currentState());
		this.fireMoveRequest(req);
	}
	
    // Create the listener list
    protected javax.swing.event.EventListenerList listenerList =
        new javax.swing.event.EventListenerList();

    // This methods allows classes to register for MyEvents
    public void addMoveRequestListener(MoveRequestEventListener listener) {
        listenerList.add(MoveRequestEventListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeMoveRequestListener(MoveRequestEventListener listener) {
        listenerList.remove(MoveRequestEventListener.class, listener);
    }

    // This private class is used to fire MyEvents
    void fireMoveRequest(MoveRequestEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i] == MoveRequestEventListener.class) {
                ((MoveRequestEventListener)listeners[i+1]).moveRequestedEventOccured(evt);
            }
        }
    }
}

