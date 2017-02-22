package team.unstudio.minigameapi.scoreboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
/**记分板API.
 * 
 * <p>记分板能显示的行数一共有16行，多出的行数不显示，而且显示是倒着的<br>
 * 例如设置行数0，0就在最底下，其他行数依次上升<br>
 * 记分板行数的设置并不一定一定是0到15，假设你设置了第16行，如果总行数未超过16行，则第16行仍然会显示<br>
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
	private final HashSet<LoopText> loopText =new HashSet<>();
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
	/**设置行数和对应的文本.
	 * 
	 * 建议使用line使用0到15<br>
	 * 
	 * 更改将会立即同步到游戏内<br>
	 * 假如之前这个行数被占用，则会覆盖掉
	 * @param line 设置的行数
	 * @param text 你要设置的文字
	 */
	public void set(int line,String text){
		if(map.containsKey(line))
			remove(line);
		map.put(line, text);
		setup();
	}
	/**设置行数和对应的循环文字.
	 * 
	 * 设置循环文字后如果设置line上已经有文本了，那么那个文本将会被<br>
	 * 当滚动文本被删除时，原文本还会出现<br>
	 * 
	 * 更改将会立即同步到游戏内
	 * @see #showNext
	 * @param line 设置的行数
	 * @param text 需要动态显示的文字
	 */
	public void setLoopDisplay(int line,String[] text){
		ArrayList<String> a = new ArrayList<>();
		for(int i=0;i<text.length;i++)
			a.add(text[i]);
		setLoopDisplay(line,a);
	}
	/**设置行数和对应的循环文字.
	 * 更改将会立即同步到游戏内
	 * 
	 * @see #showNext
	 * @param line 设置的行数
	 * @param c 需要动态显示的文字
	 */
	public void setLoopDisplay(int line,List<String> c){
		LoopText lt = new LoopText(line,c);
		loopText.add(lt);
		setup(lt.next(),line);
	}
	/**移除对应行数的循环文字.
	 * 更改将会立即同步到游戏内
	 * @param line 行数
	 */
	public void removeLoopDisplay(int line){
		LoopText lt = getLoopText(line);
		this.scoreboard.resetScores(lt.last());
		loopText.remove(lt);
		setup();
	}
	/**
	 * 移除对应行数的文字.更改将会立即同步到游戏内
	 * @param line 行数
	 * 
	 */
	public void remove(int line){
		if(map.containsKey(line)){
			this.scoreboard.resetScores(map.get(line));
			map.remove(line);
		}
	}
	private LoopText getLoopText(int line){
		Iterator<LoopText> i =loopText.iterator();
		while(i.hasNext()){
			LoopText l= i.next();
			if(l.getLine()==line)
				return l;
		}
		return null;
	}
	/**
	 * 移除对应的文字.更改将会立即同步到游戏内
	 * @param line 需要删除的字符串
	 * 
	 */
	public void remove(String line){
		this.scoreboard.resetScores(line);
		for(int i=0;i<map.size();i++)
			if(map.get(i).equals(line))
				remove(i);
	}
	/**传入的数组将会按照长度顺序将原表覆盖.
	 * 假设
	 * ["one","two","three"]
	 * 对应行数0，one ,行数1 two,行数2 three<br>
	 * 
	 * 假如行数0是scrollDisplay那么,scrollDisplay将不会被覆盖<br>
	 * 更改将会立即同步到游戏内
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
		for(int i=0;i<16;i++){
			String s = map.get(i);
			if(s==null)break;
			setup(s,i);
		}
	}
	private void setup(String s,int i){
		this.score=this.objective.getScore(s);
		this.score.setScore(1);
		this.score.setScore(i);
	}
	/**
	 * 所有循环文字显示下一个文本.
	 * 
	 * 文本是滚动的，如果滚到末尾，自动从第一位继续开始
	 */
	public void showNext(){
		Iterator<LoopText> it = loopText.iterator();
		while(it.hasNext()){
			LoopText l = it.next();
			this.scoreboard.resetScores(l.last());
			setup(l.next(),l.getLine());
		}
	}
	/**返回记分板标题
	 * 
	 * @return 记分板标题
	 */
	public String getTitle(){
		return objective.getDisplayName();
	}
	/**从行数0开始写入String数组
	 * 碰到scrollDisplay将会写入scrollDisplay内的第一个文本
	 * 
	 * @return 记分板文本
	 */
	public String[] getText(){
		String[] s = new String[16];
		for(int i=0;i<16;i++){
			LoopText l = getLoopText(i);
			if(l!=null)s[i]=l.first();
			else
				s[i]=map.get(i);
		}
		return s;
	}
	/**
	 * setTitle 设置标题.
	 * 更改将会立即同步到游戏内
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
	/**返回一个长度为16的int数组，已被使用的行数为-1.
	 * 
	 * 例：行数0,5,8被使用则
	 * [0,-1,-1,-1,5,-1,-1,8....]
	 * 
	 * @return 空置的行数
	 */
	public int[] getIdle(){
		Set<Integer> key1 = map.keySet();
		int[] value = new int[16];
		for(int i=0;i<16;i++){
			LoopText l = getLoopText(i);
			if(!key1.contains(i)){
				value[i] = i;
				break;
			}else if(l==null){
				value[i] = i;
			}else
				value[i] = -1;
		}
		return value;
	}
	@Override
	public int hashCode(){
		return map.hashCode()+loopText.hashCode()+objective.getDisplayName().hashCode();
	}
	@Override
	public boolean equals(Object o){
		if(o==null)throw new NullPointerException();
		if(!(o instanceof Board))return false;
		Board b = (Board) o;
		if(!(objective.getDisplayName().equals(b.getTitle())))return false;
		if(!(scoreboard.equals(b.getScoreboard())))return false;
		if(!(getIdle().equals(b.getIdle())))return false;
		if(!(getText().equals(b.getText())))return false;
		return true;
	}
}
