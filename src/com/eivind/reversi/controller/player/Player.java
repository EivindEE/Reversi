package com.eivind.reversi.controller.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.EventListenerList;

import com.eivind.reversi.controller.events.MovePerformedEvent;
import com.eivind.reversi.controller.events.MovePerformedEventListener;
import com.eivind.reversi.controller.events.MoveRequestEventListener;
import com.eivind.reversi.game.Color;
import com.eivind.reversi.game.ReversiGame;

public abstract class Player implements MoveRequestEventListener, MouseListener{
	protected ReversiGame game;
	protected Color color;
	
	protected Player(ReversiGame game, Color color){
		this.game = game;
		this.color = color;
	}
	
	// Listener list
	protected javax.swing.event.EventListenerList listenerList = new EventListenerList();
	
	public void addMoveEventListener(MovePerformedEventListener l){
		this.listenerList.add(MovePerformedEventListener.class, l);
	}
	
	public void removeMoveEventListener(MovePerformedEventListener l){
		this.listenerList.remove(MovePerformedEventListener.class, l);
	}
	
	protected void fireMoveEvent(MovePerformedEvent event){
		Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
		for(int i = 0; i < listeners.length; i += 2){
			if(listeners[i]== MovePerformedEventListener.class){
				((MovePerformedEventListener)listeners[i+1]).moveEventOccured(event);
			}
				
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
