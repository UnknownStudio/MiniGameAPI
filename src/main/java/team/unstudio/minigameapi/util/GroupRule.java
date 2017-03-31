package team.unstudio.minigameapi.util;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Entity;

public enum GroupRule {
	
	NO_MATE_DAMAGE;
	
	private static final Map<GroupRule,Set<Collection<Entity>>> cache = new EnumMap<>(GroupRule.class);
	
	public static void addRule(Collection<Entity> entities,GroupRule ...rules){
		for(GroupRule r:rules){
			if(!cache.containsKey(r)) 
				cache.put(r, new HashSet<>());
			cache.get(r).add(entities);
		}
	}
	
	public static void removeRule(Collection<Entity> entities,GroupRule ...rules){
		for(GroupRule r:rules){
			if(!cache.containsKey(r)) continue;
			cache.get(r).remove(entities);
		}
	}
	
	public static void clearRule(Collection<Entity> entities){
		cache.values().stream().forEach(s->s.remove(entities));
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Entity>[] getGroup(Entity entity,GroupRule rule){
		if(!cache.containsKey(rule)) return new Collection[0];
		return cache.get(rule).stream().filter(c->c.contains(entity)).toArray(i->new Collection[i]);
	}
	
	public static boolean containSameGroup(Entity entity1,Entity entity2,GroupRule rule){
		for(Collection<Entity> c:getGroup(entity1, rule))
			if(c.contains(entity2)) 
				return true;
		return false;
	}
}
