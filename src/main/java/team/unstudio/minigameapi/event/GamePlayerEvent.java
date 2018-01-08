package team.unstudio.minigameapi.event;

import org.bukkit.entity.Player;
import team.unstudio.minigameapi.game.Arena;

public abstract class GamePlayerEvent extends GameEvent {
	private final Player player;

	public GamePlayerEvent(Arena arena, Player player) {
		super(arena);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
