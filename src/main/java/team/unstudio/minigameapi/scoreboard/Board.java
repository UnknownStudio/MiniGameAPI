package team.unstudio.minigameapi.scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
/**记分板能显示的行数一共有16行，多出的行数不显示，而且显示是倒着的的<br>
 * 例如设置行数0，0就在最底下，其他行数依次上升<br>
 * 记分板行数的设置并不一定一定是0到15，假设你设置了第16行，如果总行数未超过15行，则第16行仍然会显示<br>
 * 但是如果0到15行已经设置过了，那么16行就不会显示了<br>
 * 建议使用0到15行
 * 
 * @author defoli_ation
 *
 */
public class Board {
    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private final Objective objective;
    private final TreeMap<Integer,String> map = new TreeMap<>();
    private final TreeMap<Integer,List<String>> LED = new TreeMap<>();
    private final HashMap<Integer,Integer> index = new HashMap<>();
    private Score score;
    /**构造一个显示title为标题的记分板列表
     * 
     * @param title 记分板的显示标题
     */
    public Board(String title){
    	scoreboard=Bukkit.getScoreboardManager().getNewScoreboard();
    	this.objective = scoreboard.registerNewObjective(title, "dummy");
    	this.objective.setDisplayName(title);
    	this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    /**设置行数和对应的文字
     * 
     * @param line 设置的行数
     * @param text 你要设置的文字
     */
    public void set(int line,String text){
    	map.put(line, text);
    	setup();
	}
    /**设置行数和对应的动态文字
     * 
     * @see #showNext
     * @param line 设置的行数
     * @param text 需要动态显示的文字
     */
    public void setLED(int line,String[] text){
    	ArrayList<String> a = new ArrayList<>();
    	for(int i=0;i<text.length;i++)
    		a.add(text[i]);
    	setLED(line,a);
    }
    /**设置行数和对应的动态文字
     * 
     * @see #showNext
     * @param line 设置的行数
     * @param c 需要动态显示的文字
     */
    public void setLED(int line,List<String> c){
    	LED.put(line, c);
    	index.put(line, 0);
    	setup();
    }
    /**移除对应行数的动态文字
     * 
     * @param line 行数
     */
    public void removeLED(int line){
    	this.scoreboard.resetScores(LED.get(line).get(index.get(line)));
    	index.remove(line);
    	LED.remove(line);
    }
    /**
     * 移除对应行数的文字
     * @param line 行数
     */
	public void remove(int line){
		this.scoreboard.resetScores(map.get(line));
		map.remove(line);
	}
	/**
	 * 移除对应的文字
	 * @param line 需要删除的字符串
	 */
	public void remove(String line){
		this.scoreboard.resetScores(line);
		for(int i=0;i<map.size();i++)
			if(map.get(i).equals(line))
				map.remove(i);
	}
	/**传入的数组将会按照长度顺序将原表覆盖
	 * [one,two,three]
	 * 对应行数0，one ,行数1 two,行数2 three
	 * 
	 * 假如行数0是LED那么,LED将不会被覆盖
	 * 
	 * @param text 准备显示的数组
	 */
	public void reset(String[] text){
		for(int i=0;i<text.length;i++)
			map.put(i, text[i]);
		setup();
	}
	/**返回Bukkit自带的Scoreboard类
	 * 
	 * @return org.bukkit.scoreboard.Scoreboard
	 * 
	 */
	public org.bukkit.scoreboard.Scoreboard getScoreboard(){
		return this.scoreboard;
	}
	private void setup(){
		Set<Integer> key = LED.keySet();
		for(int i=0;i<map.size();i++){
			if(!key.contains(i)){
				String s = map.get(i);
				this.score=this.objective.getScore(s);
				this.score.setScore(1);
				this.score.setScore(i);
			}else{
				this.score=this.objective.getScore(LED.get(i).get(index.get(i)));
				this.score.setScore(1);
				this.score.setScore(i);
			}
		}
	}
	/**
	 * 所有LED显示下一个文本
	 */
	public void showNext(){
		Set<Integer> key = LED.keySet();
		Iterator<Integer> it = key.iterator();
		while(it.hasNext()){
			int i = it.next();
			if(index.get(i)>=LED.get(i).size())index.put(i, 0);
			this.score=this.objective.getScore(LED.get(i).get(index.get(i)));
			this.score.setScore(1);
			this.score.setScore(i);
			index.put(i, index.get(i)+1);
		}
	}
	/**返回记分板标题
	 * 
	 * @return 记分板标题
	 */
	public String getTitle(){
		return objective.getDisplayName();
	}
	/**按照自然顺序写入String数组
	 * 碰到LED将会写入LED内的第一个文本
	 * 
	 * @return 记分板文本
	 */
	public String[] getText(){
		String[] s = new String[map.size()+LED.size()];
		Set<Integer> key = LED.keySet();
		for(int i=0;i<map.size();i++){
			if(key.contains(i))s[i]=LED.get(i).get(0);
			else
			s[i]=map.get(i);
		}
		return s;
	}
	/**
	 * setTitle 设置标题
	 * 
	 * @param title 标题
	 */
	public void setTitle(String title){
		this.objective.setDisplayName(title);
	}
	/**
	 * 
	 * @param line 行数
	 * @return 返回Bukkit的Score
	 */
	public Score getScore(String line){
		return this.objective.getScore(line);
	}
}
