package team.unstudio.minigameapi.event;
import org.bukkit.event.HandlerList;
import team.unstudio.minigameapi.game.Room;
import org.bukkit.entity.Player;

public class GamePlayerLeaveEvent extends GamePlayerEvent
{
    public GamePlayerLeaveEvent(Room room,Player player){
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
