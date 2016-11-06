package team.unstudio.minigameapi.event;
import team.unstudio.minigameapi.event.GameEvent;
import org.bukkit.event.HandlerList;
import team.unstudio.minigameapi.game.Room;

public class RoomEvent extends GameEvent
{
	private static final HandlerList handler = new HandlerList();
	
	private final Room room;
	
	public RoomEvent(Room room){
		super(room.getGame());
		this.room = room;
	}
	
	public Room getRoom(){
		return room;
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
