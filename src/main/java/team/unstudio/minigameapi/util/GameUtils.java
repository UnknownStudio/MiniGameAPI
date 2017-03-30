package team.unstudio.minigameapi.util;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public final class GameUtils {
	
	private GameUtils() {}
	
    public static void sendActionBar(Player player,String actionbar){
        
    }
    
    /**
     * 使玩家恢复到初始状态
     */
    public static void resetPlayer(Player player){
        player.setHealth(20);
        player.resetMaxHealth();
        player.setFoodLevel(20);
        player.setWalkSpeed(0.2F);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.getInventory().clear();
        player.setFireTicks(0);
    }
}
