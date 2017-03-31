package team.unstudio.minigameapi.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import team.unstudio.minigameapi.MiniGameAPI;
import team.unstudio.minigameapi.game.GameManager;

public final class MGACommandExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args[0].toLowerCase()) {
		case "version":
			sender.sendMessage("MiniGameAPI Version: "+MiniGameAPI.INSTANCE.getDescription().getVersion());
			return true;
		case "games":
			StringBuilder sb = new StringBuilder("Registed games: ");
			GameManager.getGames().stream().forEach(game->sb.append(game.getName()).append(","));
			sender.sendMessage(sb.substring(0, sb.length()-1));
			return true;
		default:
			break;
		}
		return true;
	}

}
