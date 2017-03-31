package team.unstudio.minigameapi.demo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.minigameapi.game.AbstractGame;
import team.unstudio.minigameapi.game.Room;

public class DemoGame extends AbstractGame{

	public DemoGame(JavaPlugin plugin) {
		super(plugin, "DemoGame");	
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public void onPlayerJoin(Room room, Player player) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onPlayerLeave(Room room, Player player) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onGameTick(Room room) {
		// TODO 自动生成的方法存根
		
	}
	
	@Override
	public boolean onGamePreStart(Room room) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public void onGamePostStart(Room room) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onGameEnd(Room room) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onGameStop(Room room) {
		// TODO 自动生成的方法存根
		
	}
}
