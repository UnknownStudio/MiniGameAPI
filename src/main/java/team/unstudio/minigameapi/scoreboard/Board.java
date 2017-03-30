package team.unstudio.minigameapi.scoreboard;

import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class Board {
	private final org.bukkit.scoreboard.Scoreboard scoreboard;
	private final Objective objective;
	private final TreeMap<Integer, String> map = new TreeMap<>();

	public Board(String title) {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.objective = scoreboard.registerNewObjective(title, "dummy");
		this.objective.setDisplayName(title);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public boolean set(int index, String text) {
		if (index > 15)
			return false;
		map.put(index, text);
		setup();
		return true;
	}

	public void remove(int index) {
		this.scoreboard.resetScores(map.get(index));
		map.remove(index);
	}

	public void remove(String line) {
		this.scoreboard.resetScores(line);
		for (int i = 0; i < map.size(); i++)
			if (map.get(i).equals(line))
				map.remove(i);
	}

	public void reset(String[] text) {
		for (int i = 0; i < text.length; i++)
			map.put(i, text[i]);
		setup();
	}

	public org.bukkit.scoreboard.Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	private void setup() {
		Score score;
		for (int i = 0; i < map.size(); i++) {
			String s = map.get(i);
			score = this.objective.getScore(s);
			score.setScore(1);
			score.setScore(i);
		}
	}

	public String getTitle() {
		return objective.getDisplayName();
	}

	public String[] getText() {
		String[] s = new String[map.size()];
		for (int i = 0; i < map.size(); i++) {
			s[i] = map.get(i);
		}
		return s;
	}

	public void setTitle(String title) {
		this.objective.setDisplayName(title);
	}

	public Score getScore(String line) {
		return this.objective.getScore(line);
	}
}
