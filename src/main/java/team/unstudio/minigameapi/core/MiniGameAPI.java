package team.unstudio.minigameapi.core;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.minigameapi.command.MGACommandExecutor;
import team.unstudio.minigameapi.core.listener.GameRuleListener;
import team.unstudio.minigameapi.game.Arena;

public final class MiniGameAPI extends JavaPlugin {

	private static MiniGameAPI INSTANCE;

	public static MiniGameAPI getInstance() {
		return INSTANCE;
	}

	public MiniGameAPI() {
		INSTANCE = this;
	}

	@Override
	public void onLoad() {
		ConfigurationSerialization.registerClass(Arena.class);
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();

		getServer().getPluginManager().registerEvents(new GameRuleListener(), this);

		getCommand("mga").setExecutor(new MGACommandExecutor());
	}

	@Override
	public void onDisable() {
	}
}
