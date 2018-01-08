package team.unstudio.minigameapi.event;

import org.bukkit.event.Event;
import team.unstudio.minigameapi.game.Game;
import team.unstudio.minigameapi.game.Arena;

public abstract class GameEvent extends Event {

	private final Arena arena;

	public GameEvent(Arena arena) {
		this.arena = arena;
	}

	public Arena getArena() {
		return arena;
	}

	public Game getGame() {
		return arena.getGame();
	}
}
