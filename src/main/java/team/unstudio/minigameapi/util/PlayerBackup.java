package team.unstudio.minigameapi.util;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Map;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
public class PlayerBackup implements ConfigurationSerializable
{

    private final Player player;
    
    private double health; 
    private int foodlevel;
    private int exp;
    private GameMode gamemode;
    private boolean allowFilght;
    private ItemStack[] inventory;
    
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
        Map<String,Object> map = new HashMap<>();
        map.put("health",health);
        map.put("foodlevel",foodlevel);
        map.put("exp",exp);
        map.put("allowFilght",allowFilght);
        map.put("gamemode",gamemode.getValue());
        return map;
    }
}
