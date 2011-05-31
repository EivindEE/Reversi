package com.eivind.reversi.controller.events;

import java.util.EventListener;

public interface MoveRequestEventListener extends EventListener {

	public void moveRequestedEventOccured(MoveRequestEvent event);
}
