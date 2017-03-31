package team.unstudio.minigameapi;

import java.io.File;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.minigameapi.command.MGACommandExecutor;
import team.unstudio.minigameapi.game.Room;
import team.unstudio.minigameapi.listener.GameRuleListener;
import team.unstudio.minigameapi.listener.GroupRuleListener;
import team.unstudio.minigameapi.util.I18n;

public final class MiniGameAPI extends JavaPlugin{
	
	public static MiniGameAPI INSTANCE;
	
	private static I18n I18n;
	
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
		
		I18n = new I18n(new File(getDataFolder(), "lang"));
		
		getServer().getPluginManager().registerEvents(new GameRuleListener(), this);
		getServer().getPluginManager().registerEvents(new GroupRuleListener(), this);
		
		getCommand("mga").setExecutor(new MGACommandExecutor());
	}
	
	@Override
	public void onDisable() {}

	public static I18n getI18n() {
		return I18n;
	}
}
