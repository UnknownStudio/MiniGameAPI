package team.unstudio.minigameapi.game;

import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Sets;

import java.util.Set;

import org.apache.commons.lang.Validate;

public abstract class Game implements Listener {

	private final JavaPlugin plugin;
	private final String name;
	private final Set<Arena> arenas = Sets.newHashSet();

	public Game(JavaPlugin plugin, String name) {
		Validate.notNull(plugin);
		Validate.notEmpty(name);
		this.plugin = plugin;
		this.name = name;
	}

	public final JavaPlugin getPlugin() {
		return plugin;
	}

	public final String getName() {
		return name;
	}

	public final Set<Arena> getArenas() {
		return arenas;
	}

	public boolean removeArena(Arena arena) {
		arena.disable();
		return arenas.remove(arena);
	}

	public Arena getArena(String arenaName) {
		Validate.notEmpty(arenaName);
		for (Arena room : arenas)
			if (room.getName().equals(arenaName))
				return room;
		return null;
	}

	public abstract Arena createArena(String arenaName);

	public abstract void onPlayerJoin(Arena arena, Player player);

	public abstract void onPlayerLeave(Arena arena, Player player);

	public abstract void onGameInitalize(Arena arena);

	/**
	 * @return Return <code>false</code> will cancel game start.
	 */
	public abstract boolean onGamePreStart(Arena arena);

	public abstract void onGameStarted(Arena arena);

	public abstract void onGameTick(Arena arena);

	public abstract void onGameEnd(Arena arena);

	public abstract void onGameSuspend(Arena arena);

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Game))
			return false;

		Game game = (Game) obj;

		return game.getName().equals(getName());
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
