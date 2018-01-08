package team.unstudio.minigameapi.game;

import java.util.Set;

import org.bukkit.plugin.Plugin;

import com.google.common.collect.Sets;

import java.util.HashSet;

public class GameManager {
	private static final Set<Game> GAMES = new HashSet<>();

	public static void registerGame(Game game) {
		if (GAMES.contains(game))
			throw new IllegalStateException("This game has been registed.");

		GAMES.add(game);
	}

	public static Game getGame(String name) {
		for (Game game : GAMES)
			if (game.getName().equals(name))
				return game;
		return null;
	}

	public static Set<Game> getGames() {
		return GAMES;
	}

	public static Set<Game> getGames(Plugin plugin) {
		Set<Game> games = Sets.newHashSet();
		for (Game game : GAMES)
			if (game.getPlugin().equals(plugin))
				games.add(game);
		return games;
	}
}
