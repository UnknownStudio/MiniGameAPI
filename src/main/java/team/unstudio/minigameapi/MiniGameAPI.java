package team.unstudio.minigameapi;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.minigameapi.command.MGACommandExecutor;
import team.unstudio.minigameapi.game.Room;
import team.unstudio.minigameapi.listener.GameRuleListener;

public final class MiniGameAPI extends JavaPlugin{
	
	public static MiniGameAPI INSTANCE;
	
	public MiniGameAPI() {
		INSTANCE=this;
	}
	
	@Override
	public void onLoad() {
		ConfigurationSerialization.registerClass(Room.class);
	}
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
        
		getServer().getPluginManager().registerEvents(new GameRuleListener(), this);
		
		getCommand("mga").setExecutor(new MGACommandExecutor());
	}
	
	@Override
	public void onDisable() {}
}
