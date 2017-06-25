package team.unstudio.minigameapi.util;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Entity;
import java.util.HashMap;
import java.util.Collections;

public enum GroupRule {
	
	NO_MATE_DAMAGE;
	
	private static final Map<EntityGroup,Set<GroupRule>> cache = new HashMap<>();
	
	public static void addRule(EntityGroup group,GroupRule ...rules){
		if(!cache.containsKey(group)) 
			cache.put(group, new HashSet<>());
		Collections.addAll(cache.get(group),rules);
	}
	
	public static void removeRule(EntityGroup group,GroupRule ...rules){
	    if(!cache.containsKey(group))
            return;

        Set<GroupRule> set = cache.get(group);
        for(GroupRule rule:rules)
            set.remove(rule);
	}
	
	public static void clearRule(EntityGroup group){
		cache.remove(group);
	}
    
    public static boolean hasRule(EntityGroup group,GroupRule rule){
        if(!cache.containsKey(group)) 
            return false;
            
        return cache.get(group).contains(rule);
    }
    
    public static Set<GroupRule> getRules(EntityGroup group){
        if(!cache.containsKey(group))
            return Collections.emptySet();
        return cache.get(group);
    }
	
	@SuppressWarnings("unchecked")
	public static Set<EntityGroup> getGroup(Entity entity){
        Set<EntityGroup> groups = new HashSet();
        for(EntityGroup group:cache.keySet())
            if(group.contains(entity))
                groups.add(group);
        return groups;
    }
    
    public static Set<EntityGroup> getSameGroup(Entity entity1,Entity entity2){
        Set<EntityGroup> groups = new HashSet();
        for(EntityGroup group:getGroup(entity1))
            if(group.contains(entity2)) 
                groups.add(group);
        return groups;
	}
    
    public static boolean containSameGroup(Entity entity1,Entity entity2){
        for(EntityGroup group:getGroup(entity1))
            if(group.contains(entity2)) 
                return true;
        return false;
	}
	
	public static boolean containSameGroupAndHasRule(Entity entity1,Entity entity2,GroupRule rule){
		for(EntityGroup group:getSameGroup(entity1,entity2))
			if(hasRule(group,rule)) 
				return true;
		return false;
	}
}
