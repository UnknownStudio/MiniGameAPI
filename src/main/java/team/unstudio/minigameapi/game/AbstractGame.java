package team.unstudio.minigameapi.game;

import org.bukkit.event.Listener;
import org.bukkit.entity.Player;

public abstract class AbstractGame implements Listener{
    
    private final String name;
    
    public AbstractGame(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
	
	public abstract void onPlayerJoin(Room room,Player player);
    
    public abstract void onPlayerLeave(Room room,Player player);
    
    public abstract void onTick(Room room);
    
    public abstract void onGameStart(Room room);
    
    public abstract void onGameEnd(Room room);
    
    public abstract void onGameStop(Room room);
}
