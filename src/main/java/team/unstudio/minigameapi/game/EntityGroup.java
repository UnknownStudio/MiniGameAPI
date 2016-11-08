package team.unstudio.minigameapi.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.Random;

public class EntityGroup extends HashSet<Entity>{
	
	public EntityGroup() {}
	
	public EntityGroup(Collection<Entity> c) {
	      super(c);
	}
	
	public EntityGroup filter(Filter filter){
		EntityGroup group = new EntityGroup();
		
		for(Entity e:this) if(filter.filter(e)) group.add(e);
		
		return group;
	}
    
    public void sendMessage(String message){
        for(Entity e:this) if(e instanceof Player) e.sendMessage(message);
    }
    
    public void teleport(Location location){
        for(Entity e:this) e.teleport(location);
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
