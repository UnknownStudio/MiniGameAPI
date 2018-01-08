package team.unstudio.minigameapi.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import team.unstudio.minigameapi.game.Arena;
import team.unstudio.minigameapi.game.Game;

public final class GameUtils {

	private GameUtils() {
	}

	public static Game getGame(Player player) {
		return Arena.isInArena(player) ? Arena.getArena(player).getGame() : null;
	}

	public static boolean isInGame(Player player) {
		return Arena.isInArena(player);
	}

	public static Arena getArena(Player player) {
		return Arena.getArena(player);
	}

	/**
	 * 使玩家恢复到初始状态
	 */
	@SuppressWarnings("deprecation")
	public static void resetPlayer(Player player) {
		player.resetMaxHealth();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setWalkSpeed(0.2F);
		player.setTotalExperience(0);
		player.setLevel(0);
		player.setExp(0);
		player.setGameMode(GameMode.SURVIVAL);
		player.setFlying(false);
		player.setAllowFlight(false);
		player.getInventory().clear();
		player.setFireTicks(0);
	}
}
