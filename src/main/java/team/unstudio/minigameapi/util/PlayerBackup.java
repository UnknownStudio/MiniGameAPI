package team.unstudio.minigameapi.util;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import org.bukkit.GameMode;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Map;
public class PlayerBackup implements ConfigurationSerializable
{

    private final Player player;
    
    private double health; 
    private int foodlevel;
    private int exp;
    private GameMode gamemode;
    private boolean allowFilght;
    
    public PlayerBackup(Player player){
        this.player = player;
    }
    
    public void save(){
        health = player.getHealth();
        foodlevel = player.getFoodLevel();
        exp = player.getTotalExperience();
        gamemode = player.getGameMode();
        allowFilght = player.getAllowFlight();
    }
    
    public void clear(){
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
    }
    
    public void load(){
        player.setHealth(health);
        player.setFoodLevel(foodlevel);
        player.giveExp(exp);
        player.setGameMode(gamemode);
        player.setAllowFlight(allowFilght);
    }
    
    public Player getPlayer(){
        return player;
    }
    
    @Override
    public Map<String, Object> serialize()
    {
        // TODO: Implement this method
        return null;
    }
}
