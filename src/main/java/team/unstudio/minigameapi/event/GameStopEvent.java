package team.unstudio.minigameapi.event;
import org.bukkit.event.HandlerList;
import team.unstudio.minigameapi.game.Room;

public class GameStopEvent extends GameEvent
{
    public GameStopEvent(Room room){
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
