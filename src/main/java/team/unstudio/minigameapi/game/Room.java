package team.unstudio.minigameapi.game;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Map;
import org.bukkit.Bukkit;
import team.unstudio.minigameapi.event.GamePlayerJoinEvent;
import team.unstudio.minigameapi.event.GamePlayerLeaveEvent;
import team.unstudio.minigameapi.event.GameStartEvent;
import team.unstudio.minigameapi.event.GameEndEvent;
import team.unstudio.minigameapi.event.GameStopEvent;

public class Room extends BukkitRunnable implements ConfigurationSerializable
{
	
	private final AbstractGame game;
	private final String name;
	protected final EntityGroup players = new EntityGroup();
	
	private RoomState state = RoomState.DISABLED;
    private long tick=0;
	
	public Room(AbstractGame game,String name) {
		this.game = game;
		this.name = name;
	}

	public EntityGroup getPlayers() {
		return players;
	}

	public AbstractGame getGame() {
		return game;
	}
	
	public String getName(){
		return name;
	}
	
	public RoomState getState(){
		return state;
	}
	
	public boolean joinPlayer(Player player){
		if(players.contains(player))return true;
	    
		if(state!=RoomState.WAITING)return false;
		
        Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(this,player));
        
        game.onPlayerJoin(this,player);
        
		players.add(player);
		
		return true;
	}
	
	public boolean leavePlayer(Player player){
		if(!players.contains(player))return true;
        
        Bukkit.getPluginManager().callEvent(new GamePlayerLeaveEvent(this,player));
        
        game.onPlayerLeave(this,player);
		
		players.remove(player);
		
		return true;
	}
    
    public void setEnable(boolean enable){
        state = enable?RoomState.WAITING:RoomState.DISABLED;
    }
    
    public boolean isEnable(){
        return state!=RoomState.DISABLED;
    }
    
    public void start(){
        synchronized(this){
             if(state!=RoomState.WAITING) return;
             state = RoomState.STARTING;
        }
        
        tick=0;
        
        Bukkit.getPluginManager().callEvent(new GameStartEvent(this));
        
        game.onGameStart(this);
    }
    
    public void stop(){
        synchronized(this){
            if(state!=RoomState.PLAYING) return;
            state=RoomState.ENDING;
        }
        
        Bukkit.getPluginManager().callEvent(new GameStopEvent(this));
        
        game.onGameStop(this);
        
    }
    
    public void end(){
        synchronized(this){
             if(state!=RoomState.PLAYING) return;
             state=RoomState.ENDING;
        }
        
        Bukkit.getPluginManager().callEvent(new GameEndEvent(this));
        
        game.onGameEnd(this);
    }
    
    public long getTick(){
        return tick;
    }
    
	@Override
	public void run()
	{
        tick++;
		game.onTick(this);
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		// TODO: Implement this method
		return null;
	}
}
