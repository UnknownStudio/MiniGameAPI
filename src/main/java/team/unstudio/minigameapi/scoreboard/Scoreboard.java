package team.unstudio.minigameapi.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scoreboard {
	//最大行数16       每行最大字符40
	public Scoreboard(){
		
	}
	public static void setPlayerScoreboard(Player p,Board board){
		p.setScoreboard(board.getScoreboard());
	}
	public static void clear(Player p){
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
