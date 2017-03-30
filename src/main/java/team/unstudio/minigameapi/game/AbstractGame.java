package team.unstudio.minigameapi.game;

import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Set;
import java.util.HashSet;
import org.bukkit.command.CommandExecutor;

public abstract class AbstractGame implements Listener,CommandExecutor{
    
    private final JavaPlugin plugin;
    private final String name;
    private final Set<Room> rooms = new HashSet<>();
    
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
    
    public Set<Room> getRooms(){
        return rooms;
    }
    
    public boolean addRoom(Room room){
        return rooms.add(room);
    }
    
    public boolean removeRoom(Room room){
        room.dispose();
        return rooms.remove(room);
    }
    
    public Room getRoom(String name){
        for(Room room:rooms) if(room.getName().equals(name)) return room;
        return null;
    }
	
	public abstract void onPlayerJoin(Room room,Player player);
    
    public abstract void onPlayerLeave(Room room,Player player);
    
    public abstract void onGameTick(Room room);
    
    public abstract void onGameStart(Room room);
    
    public abstract void onGameEnd(Room room);
    
    public abstract void onGameStop(Room room);
    
    @Override
    public int hashCode(){
    	return name.hashCode();
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj==null) return false;
        
        if(!(obj instanceof AbstractGame)) return false;
        
        AbstractGame o = (AbstractGame) obj;
        
        if(!o.getName().equals(getName())) return false;
        
        return true;
    }
}
