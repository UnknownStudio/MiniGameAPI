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
/**�Ƿְ�API.
 * 
 * <p>�Ƿְ�����ʾ������һ����16�У��������������ʾ��������ʾ�ǵ��ŵ�<br>
 * ������������0��0��������£�����������������<br>
 * �Ƿְ����������ò���һ��һ����0��15�������������˵�16�У����������δ����16�У����16����Ȼ����ʾ<br>
 * �������0��15���Ѿ����ù��ˣ���ô16�оͲ�����ʾ��<br>
 * ����ʹ��0��15��
 * 
 * @author defoli_ation
 *
 */
public class Board {
    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private final Objective objective;
    private final TreeMap<Integer,String> map = new TreeMap<>();
    private final TreeMap<Integer,List<String>> scrollDisplay = new TreeMap<>();
    private final HashMap<Integer,Integer> index = new HashMap<>();
    private Score score;
    /**����һ����ʾtitleΪ����ļǷְ��б�
     * 
     * @param title �Ƿְ����ʾ����
     */
    public Board(String title){
    	scoreboard=Bukkit.getScoreboardManager().getNewScoreboard();
    	this.objective = scoreboard.registerNewObjective(title, "dummy");
    	this.objective.setDisplayName(title);
    	this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    /**���������Ͷ�Ӧ���ı�.
     * 
     * ����ʹ��lineʹ��0��15<br>
     * 
     * ���Ľ�������ͬ������Ϸ��
     * @param line ���õ�����
     * @param text ��Ҫ���õ�����
     */
    public void set(int line,String text){
    	map.put(line, text);
    	setup();
	}
    /**���������Ͷ�Ӧ�Ĺ�������.
     * 
     * ���ù������ֺ��������line���Ѿ����ı��ˣ���ô�Ǹ��ı����ᱻ<br>
     * �������ı���ɾ��ʱ��ԭ�ı��������<br>
     * 
     * ���Ľ�������ͬ������Ϸ��
     * @see #showNext
     * @param line ���õ�����
     * @param text ��Ҫ��̬��ʾ������
     */
    public void setScrollDisplay(int line,String[] text){
    	ArrayList<String> a = new ArrayList<>();
    	for(int i=0;i<text.length;i++)
    		a.add(text[i]);
    	setScrollDisplay(line,a);
    }
    /**���������Ͷ�Ӧ�Ĺ�������.
     * ���Ľ�������ͬ������Ϸ��
     * 
     * @see #showNext
     * @param line ���õ�����
     * @param c ��Ҫ��̬��ʾ������
     */
    public void setScrollDisplay(int line,List<String> c){
    	scrollDisplay.put(line, c);
    	index.put(line, 0);
    	setup();
    }
    /**�Ƴ���Ӧ�����Ķ�̬����.
     * ���Ľ�������ͬ������Ϸ��
     * @param line ����
     */
    public void removeScrollDisplay(int line){
    	this.scoreboard.resetScores(scrollDisplay.get(line).get(index.get(line)));
    	index.remove(line);
    	scrollDisplay.remove(line);
    	setup();
    }
    /**
     * �Ƴ���Ӧ����������.���Ľ�������ͬ������Ϸ��
     * @param line ����
     * 
     */
	public void remove(int line){
		this.scoreboard.resetScores(map.get(line));
		map.remove(line);
	}
	/**
	 * �Ƴ���Ӧ������.���Ľ�������ͬ������Ϸ��
	 * @param line ��Ҫɾ�����ַ���
	 * 
	 */
	public void remove(String line){
		this.scoreboard.resetScores(line);
		for(int i=0;i<map.size();i++)
			if(map.get(i).equals(line))
				map.remove(i);
	}
	/**��������齫�ᰴ�ճ���˳��ԭ����.
	 * ����
	 * ["one","two","three"]
	 * ��Ӧ����0��one ,����1 two,����2 three<br>
	 * 
	 * ��������0��scrollDisplay��ô,scrollDisplay�����ᱻ����<br>
	 * ���Ľ�������ͬ������Ϸ��
	 * 
	 * @param text ׼����ʾ������
	 */
	public void reset(String[] text){
		for(int i=0;i<text.length;i++)
			map.put(i, text[i]);
		setup();
	}
	/**����Bukkit�Դ���Scoreboard��
	 * 
	 * @return org.bukkit.scoreboard.Scoreboard
	 * 
	 */
	public org.bukkit.scoreboard.Scoreboard getScoreboard(){
		return this.scoreboard;
	}
	private void setup(){
		Set<Integer> key = scrollDisplay.keySet();
		for(int i=0;i<map.size();i++){
			if(!key.contains(i)){
				String s = map.get(i);
				this.score=this.objective.getScore(s);
				this.score.setScore(1);
				this.score.setScore(i);
			}else{
				this.score=this.objective.getScore(scrollDisplay.get(i).get(index.get(i)));
				this.score.setScore(1);
				this.score.setScore(i);
			}
		}
	}
	/**
	 * ����scrollDisplay��ʾ��һ���ı�.
	 * 
	 * �ı��ǹ����ģ��������ĩβ���Զ��ӵ�һλ������ʼ
	 */
	public void showNext(){
		Set<Integer> key = scrollDisplay.keySet();
		Iterator<Integer> it = key.iterator();
		while(it.hasNext()){
			int i = it.next();
			if(index.get(i)>=scrollDisplay.get(i).size())index.put(i, 0);
			this.score=this.objective.getScore(scrollDisplay.get(i).get(index.get(i)));
			this.score.setScore(1);
			this.score.setScore(i);
			index.put(i, index.get(i)+1);
		}
	}
	/**���ؼǷְ����
	 * 
	 * @return �Ƿְ����
	 */
	public String getTitle(){
		return objective.getDisplayName();
	}
	/**������0��ʼд��String����
	 * ����scrollDisplay����д��scrollDisplay�ڵĵ�һ���ı�
	 * 
	 * @return �Ƿְ��ı�
	 */
	public String[] getText(){
		String[] s = new String[map.size()+scrollDisplay.size()];
		Set<Integer> key = scrollDisplay.keySet();
		for(int i=0;i<map.size();i++){
			if(key.contains(i))s[i]=scrollDisplay.get(i).get(0);
			else
			s[i]=map.get(i);
		}
		return s;
	}
	/**
	 * setTitle ���ñ���.
	 * ���Ľ�������ͬ������Ϸ��
	 * 
	 * @param title ����
	 */
	public void setTitle(String title){
		this.objective.setDisplayName(title);
	}
	/**
	 * 
	 * @param line ����
	 * @return ����Bukkit��Score
	 */
	public Score getScore(String line){
		return this.objective.getScore(line);
	}
	/**����һ������Ϊ16��int���飬�ѱ�ʹ�õ�����Ϊ-1.
	 * 
	 * ��������0,5,8��ʹ����
	 * [0,-1,-1,-1,5,-1,-1,8....]
	 * 
	 * @return ���õ�����
	 */
	public int[] getIdle(){
		Set<Integer> key1 = map.keySet();
		Set<Integer> key2 = scrollDisplay.keySet();
		int[] value = new int[16];
		for(int i=0;i<16;i++){
			if(!key1.contains(i)){
				value[i] = i;
				break;
			}else if(!key2.contains(i)){
				value[i] = i;
			}else
				value[i] = -1;
		}
		return value;
	}
	@Override
	public int hashCode(){
		return map.hashCode()+index.hashCode()+scrollDisplay.hashCode()+objective.getDisplayName().hashCode();
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
