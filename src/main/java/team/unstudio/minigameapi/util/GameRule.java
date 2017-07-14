package team.unstudio.minigameapi.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Entity;

import com.google.common.collect.Maps;

import java.util.Collections;

public enum GameRule{
	
	NO_PVP,NO_MOVE;
	
	private static final Map<Entity,Set<GameRule>> cache = Maps.newHashMap();
	
	public static void addRule(Entity entity,GameRule ...rules){
		if(!cache.containsKey(entity))
			cache.put(entity, new HashSet<>());
		Collections.addAll(cache.get(entity),rules);
	}
	
	public static void addRule(Collection<Entity> entities,GameRule ...rules){
		for(Entity e:entities)
            addRule(e,rules);
	}
	
	public static void removeRule(Entity entity,GameRule ...rules){
		if(!cache.containsKey(entity))
            return;
            
		Set<GameRule> set = cache.get(entity);
        for(GameRule rule:rules)
            set.remove(rule);
	}
	
	public static void removeRule(Collection<Entity> entities,GameRule ...rules){
        for(Entity e:entities)
            removeRule(e,rules);
	}
	
	public static void clearRule(Entity entity){
		if(cache.containsKey(entity))
            cache.remove(entity);
	}
	
	public static void clearRule(Collection<Entity> entities){
		for(Entity e:entities)
            clearRule(e);
	}
	
	public static boolean hasRule(Entity entity,GameRule rule){
		if(!cache.containsKey(entity)) return false;
		return cache.get(entity).contains(rule);
	}
    
    public static Set<GameRule> getEntityRules(Entity entity){
        if(!cache.containsKey(entity))
            return Collections.emptySet();
        return Collections.unmodifiableSet(cache.get(entity));
    }
}
