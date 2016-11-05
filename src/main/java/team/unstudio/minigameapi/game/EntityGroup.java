package team.unstudio.minigameapi.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;

public class EntityGroup{

	protected final Set<Entity> eneities = new HashSet<>();
	
	public EntityGroup() {}
	
	public EntityGroup(Collection<Entity> c) {
		eneities.addAll(c);
	}

	public Set<Entity> getEneities() {
		return eneities;
	}
	
}
