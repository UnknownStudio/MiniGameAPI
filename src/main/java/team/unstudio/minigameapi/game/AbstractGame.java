package team.unstudio.minigameapi.game;

import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractGame implements Listener{
    
    private final JavaPlugin plugin;
    private final String name;
    
    public AbstractGame(JavaPlugin plugin,String name){
        this.plugin = plugin;
        this.name = name;
    }
    
    public JavaPlugin getPlugin(){
        return plugin;
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
    
    @Override
    public boolean equals(Object obj){
        //Todo:equals
        return false;
    }
}
