package team.unstudio.minigameapi;

import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.minigameapi.listener.GameRuleListener;

public final class MiniGameAPI extends JavaPlugin{
	
	public static MiniGameAPI INSTANCE;
	
	public MiniGameAPI() {
		INSTANCE=this;
	}
	
	@Override
	public void onLoad() {}
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new GameRuleListener(), this);
	}
	
	@Override
	public void onDisable() {}

}
