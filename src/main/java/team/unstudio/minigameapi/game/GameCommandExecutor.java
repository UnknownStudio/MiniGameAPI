package team.unstudio.minigameapi.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args[0].toLowerCase()) {
		case "create":

			return true;
		case "remove":

			return true;
		case "start":

			return true;
		case "stop":

			return true;
		case "join":

			return true;
		case "leave":

			return true;
		case "kick":

			return true;
		case "arenas":

			return true;
		case "set":

			return true;
		default:
			return true;
		}
	}

}
