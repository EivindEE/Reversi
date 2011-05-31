package com.eivind.reversi.controller.events;

import java.util.EventListener;

public interface MovePerformedEventListener extends EventListener {

	public void moveEventOccured(MovePerformedEvent event);

}
