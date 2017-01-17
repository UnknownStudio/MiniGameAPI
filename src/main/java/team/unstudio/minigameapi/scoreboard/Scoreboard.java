package team.unstudio.minigameapi.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scoreboard {
	public Scoreboard(){
		
	}
	public static void setPlayerScoreboard(Player p,Board board){
		p.setScoreboard(board.getScoreboard());
	}
	public static void clear(Player p){
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
