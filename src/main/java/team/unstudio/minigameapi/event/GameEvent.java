package team.unstudio.minigameapi.event;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import team.unstudio.minigameapi.game.AbstractGame;

public class GameEvent extends Event
{
	private static final HandlerList handler = new HandlerList();
	
	private final AbstractGame game;
	
	public GameEvent(AbstractGame game){
		this.game = game;
	}
	
	public AbstractGame getGame(){
		return game;
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
