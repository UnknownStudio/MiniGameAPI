package team.unstudio.minigameapi.util;

import java.util.Collection;
import java.util.HashSet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Location;
import java.util.Random;
import java.util.function.Predicate;

public class EntityGroup<T extends Entity> extends HashSet<T> {

	public EntityGroup() {
	}

	public EntityGroup(Collection<T> c) {
		super(c);
	}

	public EntityGroup<Entity> filter(Predicate<Entity> filter) {
		EntityGroup<Entity> group = new EntityGroup<>();

		for (Entity entity : this)
			if (filter.test(entity))
				group.add(entity);

		return group;
	}

	public void sendMessage(String message) {
		for (Entity e : this)
			if (e instanceof Player)
				e.sendMessage(message);
	}

	public void sendMessage(BaseComponent message) {
		for (Entity e : this)
			if (e instanceof Player)
				((Player) e).spigot().sendMessage(message);
	}

	public void teleport(Location location) {
		for (Entity e : this)
			e.teleport(location);
	}

	public void teleport(Location location, double radius) {
		Random random = new Random();

		for (Entity e : this) {
			double x = random.nextDouble() * radius * (random.nextBoolean() ? 1 : -1);
			double y = Math.sqrt(radius * radius - x * x) * (random.nextBoolean() ? 1 : -1);
			e.teleport(location.add(x, 0, y));
		}
	}

	public void giveItem(ItemStack... items) {
		for (Entity e : this)
			if (e instanceof Player)
				((Player) e).getInventory().addItem(items);
	}
}
