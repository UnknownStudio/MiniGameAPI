package team.unstudio.minigameapi.event;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import team.unstudio.minigameapi.game.Room;

public class GameStartEvent extends GameEvent implements Cancellable{
	
	private boolean cancelled = false;
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancelled = arg0;
	}
	
    public GameStartEvent(Room room){
        super(room);
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
