package team.unstudio.minigameapi.event;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import team.unstudio.minigameapi.game.AbstractGame;
import team.unstudio.minigameapi.game.Room;

public class GameEvent extends Event
{
	private static final HandlerList handler = new HandlerList();
	
	private final Room room;
	
	public GameEvent(Room room){
		this.room = room;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public AbstractGame getGame(){
		return room.getGame();
	}
	
	@Override
	public HandlerList getHandlers()
	{
		return handler;
	}
	
	public static HandlerList getHandlerList(){
		return handler;
	}
}
