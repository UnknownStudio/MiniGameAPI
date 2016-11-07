package team.unstudio.minigameapi.event;
import org.bukkit.entity.Player;
import team.unstudio.minigameapi.game.Room;
import org.bukkit.event.HandlerList;

public class GamePlayerEvent extends GameEvent
{
	private final Player player;
	
	public GamePlayerEvent(Room room,Player player){
		super(room);
		this.player=player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	private static final HandlerList handler = new HandlerList();
	
	@Override
	public HandlerList getHandlers()
	{
		return handler;
	}

	public static HandlerList getHandlerList(){
		return handler;
	}
}
