package team.unstudio.minigameapi.game;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class Room {
	
	private final AbstractGame game;
	protected final Set<Player> players = new HashSet<>();
	
	public Room(AbstractGame game) {
		this.game = game;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public AbstractGame getGame() {
		return game;
	}
}
