package team.unstudio.minigameapi.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;

public class EntityGroup extends HashSet<Entity>{
	
	public EntityGroup() {}
	
	public EntityGroup(Collection<Entity> c) {
	      super(c);
	}
	
	public EntityGroup filter(Filter f){
		EntityGroup group = new EntityGroup();
		
		for(Entity e:this) if(f.filter(e)) group.add(e);
		
		return group;
	}
	
	
	
	public interface Filter{
		
		boolean filter(Entity e);
	}
}