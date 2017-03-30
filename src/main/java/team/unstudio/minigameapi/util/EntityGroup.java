package team.unstudio.minigameapi.util;

import java.util.Collection;
import java.util.HashSet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.Random;

public class EntityGroup<T extends Entity> extends HashSet<T>{
	
	public EntityGroup() {}
	
	public EntityGroup(Collection<T> c) {
	      super(c);
	}
	
	public EntityGroup<Entity> filter(Filter filter){
		EntityGroup<Entity> group = new EntityGroup<>();
		
		for(Entity e:this) if(filter.filter(e)) group.add(e);
		
		return group;
	}
    
    public void sendMessage(String message){
        for(Entity e:this) 
        	if(e instanceof Player) 
        		e.sendMessage(message);
    }
    
    public void teleport(Location location){
        for(Entity e:this) 
        	e.teleport(location);
    }
    
    public void teleport(Location location,double radius){
        Random random = new Random();
        
        for(Entity e:this){
            double x = random.nextDouble()*radius*(random.nextBoolean()?1:-1);
            double y = Math.sqrt(radius*radius-x*x)*(random.nextBoolean()?1:-1);
            e.teleport(location.add(x,0,y));
        }
    }
	
	public interface Filter{
		
		boolean filter(Entity entity);
	}
}
