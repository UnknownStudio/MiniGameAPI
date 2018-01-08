package team.unstudio.minigameapi.event;

import org.bukkit.event.HandlerList;

import team.unstudio.minigameapi.game.Arena;

public class GameInitalizeEvent extends GameEvent {

	public GameInitalizeEvent(Arena arena) {
		super(arena);
	}

	private static final HandlerList handler = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handler;
	}

	public static HandlerList getHandlerList() {
		return handler;
	}
}
