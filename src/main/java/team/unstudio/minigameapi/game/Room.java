package team.unstudio.minigameapi.game;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Map;

public class Room extends BukkitRunnable implements ConfigurationSerializable
{
	
	private final AbstractGame game;
	protected final EntityGroup players = new EntityGroup();
	
	private RoomState state = RoomState.DISABLED;
	
	public Room(AbstractGame game) {
		this.game = game;
	}

	public EntityGroup getPlayers() {
		return players;
	}

	public AbstractGame getGame() {
		return game;
	}
	
	public RoomState getState(){
		return state;
	}
	
	public boolean joinPlayer(Player player){
		if(players.contains(player))return true;
	    
		if(state!=RoomState.WAITING)return false;
		
		return true;
	}
	
	public boolean leavePlayer(Player player){
		if(!players.contains(player))return true;
		
		return true;
	}

	@Override
	public void run()
	{
		// TODO: Implement this method
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		// TODO: Implement this method
		return null;
	}
}
