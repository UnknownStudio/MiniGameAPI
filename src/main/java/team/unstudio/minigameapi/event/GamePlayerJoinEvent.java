package team.unstudio.minigameapi.event;
import team.unstudio.minigameapi.game.Room;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GamePlayerJoinEvent extends GamePlayerEvent implements Cancellable
{
	private boolean cancelled = false;
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancelled = arg0;
	}
	
    public GamePlayerJoinEvent(Room room,Player player){
        super(room,player);
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
