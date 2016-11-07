package team.unstudio.minigameapi.event;
import team.unstudio.minigameapi.game.Room;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class GamePlayerJoinEvent extends GamePlayerEvent
{
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
