package team.unstudio.minigameapi.util;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Map;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
public class PlayerBackup implements ConfigurationSerializable
{

    private final Player player;
    
    private double health; 
    private double maxHealth;
    private int foodlevel;
    private int exp;
    private GameMode gamemode;
    private boolean allowFilght;
    private ItemStack[] content;
    private ItemStack[] armor;
    private ItemStack[] extra;
    
    public PlayerBackup(Player player){
        this.player = player;
    }
    
    public void save(){
        health = player.getHealth();
        maxHealth = player.getMaxHealth();
        foodlevel = player.getFoodLevel();
        exp = player.getTotalExperience();
        gamemode = player.getGameMode();
        allowFilght = player.getAllowFlight();
        
        //save inventory
        PlayerInventory inventory = player.getInventory();
        {
        ItemStack[] content = inventory.getContents();
        this.content = new ItemStack[content.length];
        for(int i=0;i<content.length;i++)
            this.content[i] = content[i];
        }{
        ItemStack[] armor = inventory.getArmorContents();
        this.armor = new ItemStack[armor.length];
        for(int i=0;i<armor.length;i++)
            this.armor[i] = armor[i];
        }{
        ItemStack[] extra = inventory.getExtraContents();
        this.extra = new ItemStack[extra.length];
        for(int i=0;i<extra.length;i++)
            this.extra[i] = extra[i];
        }
    }
    
    public void load(){
        player.setMaxHealth(maxHealth);
        player.setHealth(health);
        player.setFoodLevel(foodlevel);
        player.giveExp(exp);
        player.setGameMode(gamemode);
        player.setAllowFlight(allowFilght);
        player.getInventory().setContents(content);
        player.getInventory().setArmorContents(armor);
        player.getInventory().setExtraContents(extra);
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
