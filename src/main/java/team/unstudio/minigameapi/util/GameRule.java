package team.unstudio.minigameapi.util;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Entity;

public enum GameRule{
	
	NO_PVP,NO_MOVE;
	
	private static final Map<GameRule,Set<Entity>> cache = new EnumMap<>(GameRule.class);
	
	public static void addRule(Entity entity,GameRule ...rules){
		for(GameRule r:rules){
			if(!cache.containsKey(r))
				cache.put(r, new HashSet<>());
			cache.get(r).add(entity);
		}
	}
	
	public static void addRule(Collection<Entity> entities,GameRule ...rules){
		entities.stream().forEach(e->addRule(e,rules));
	}
	
	public static void removeRule(Entity entity,GameRule ...rules){
		for(GameRule r:rules){
			if(!cache.containsKey(r)) continue;
			cache.get(r).remove(entity);
		}
	}
	
	public static void removeRule(Collection<Entity> entities,GameRule ...rules){
		entities.stream().forEach(e->removeRule(e,rules));
	}
	
	public static void clearRule(Entity entity){
		cache.values().stream().forEach(s->s.remove(entity));
	}
	
	public static void clearRule(Collection<Entity> entities){
		entities.stream().forEach(e->clearRule(e));
	}
	
	public static boolean hasRule(Entity entity,GameRule rule){
		if(!cache.containsKey(rule)) return false;
		return cache.get(rule).contains(entity);
	}
}
