package team.unstudio.minigameapi.scoreboard;

import java.util.Set;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import team.unstudio.minigameapi.game.Room;

public class Board {
    private final Room room;
    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private final Objective objective;
    private final TreeMap<Integer,String> map = new TreeMap<>();
    public Board(Room room,String title){
    	this.room=room;
    	scoreboard=Bukkit.getScoreboardManager().getNewScoreboard();
    	this.objective = scoreboard.registerNewObjective(title, "dummy");
    	this.objective.setDisplayName(title);
    }
    public Room getRoom(){
    	return room;
    }
    public void set(int index,String text){
    	if(text.length()>40)text=text.substring(0, 41);
    	map.put(index, text);
    	setup();
	}
	public void remove(int index){
		map.remove(index);
		setup();
	}
	public void reset(String[] text){
		for(int i=0;i<text.length;i++){
			String s= text[i];
			if(s.length()>40)s=s.substring(0, 41);
			map.put(i, s);
		}
		setup();
	}
	public org.bukkit.scoreboard.Scoreboard getScoreboard(){
		return scoreboard;
	}
	private void setup(){
		Score score;
		Set<Integer> key = map.keySet();
		for(int i : key){
			String s = map.get(i);
			score=objective.getScore(s);
			score.setScore(i);
		}
	}
}
