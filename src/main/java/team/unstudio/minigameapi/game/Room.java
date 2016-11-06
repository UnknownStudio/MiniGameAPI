package team.unstudio.minigameapi.game;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Room extends BukkitRunnable{
	
	private final AbstractGame game;
	protected final Set<Player> players = new HashSet<>();
	
	public RoomState state;
	
	public Room(AbstractGame game) {
		this.game = game;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public AbstractGame getGame() {
		return game;
	}
	
	public RoomState getState(){
		return state;
	}

	@Override
	public void run()
	{
		// TODO: Implement this method
	}
}
