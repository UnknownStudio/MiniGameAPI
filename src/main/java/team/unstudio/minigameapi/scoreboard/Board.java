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
/**�Ƿְ�����ʾ������һ����16�У��������������ʾ��������ʾ�ǵ��ŵĵ�<br>
 * ������������0��0��������£�����������������<br>
 * �Ƿְ����������ò���һ��һ����0��15�������������˵�16�У����������δ����15�У����16����Ȼ����ʾ<br>
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
    private final TreeMap<Integer,List<String>> LED = new TreeMap<>();
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
    /**���������Ͷ�Ӧ������
     * 
     * @param line ���õ�����
     * @param text ��Ҫ���õ�����
     */
    public void set(int line,String text){
    	map.put(line, text);
    	setup();
	}
    /**���������Ͷ�Ӧ�Ķ�̬����
     * 
     * @see #showNext
     * @param line ���õ�����
     * @param text ��Ҫ��̬��ʾ������
     */
    public void setLED(int line,String[] text){
    	ArrayList<String> a = new ArrayList<>();
    	for(int i=0;i<text.length;i++)
    		a.add(text[i]);
    	setLED(line,a);
    }
    /**���������Ͷ�Ӧ�Ķ�̬����
     * 
     * @see #showNext
     * @param line ���õ�����
     * @param c ��Ҫ��̬��ʾ������
     */
    public void setLED(int line,List<String> c){
    	LED.put(line, c);
    	index.put(line, 0);
    	setup();
    }
    /**�Ƴ���Ӧ�����Ķ�̬����
     * 
     * @param line ����
     */
    public void removeLED(int line){
    	this.scoreboard.resetScores(LED.get(line).get(index.get(line)));
    	index.remove(line);
    	LED.remove(line);
    }
    /**
     * �Ƴ���Ӧ����������
     * @param line ����
     */
	public void remove(int line){
		this.scoreboard.resetScores(map.get(line));
		map.remove(line);
	}
	/**
	 * �Ƴ���Ӧ������
	 * @param line ��Ҫɾ�����ַ���
	 */
	public void remove(String line){
		this.scoreboard.resetScores(line);
		for(int i=0;i<map.size();i++)
			if(map.get(i).equals(line))
				map.remove(i);
	}
	/**��������齫�ᰴ�ճ���˳��ԭ����
	 * [one,two,three]
	 * ��Ӧ����0��one ,����1 two,����2 three
	 * 
	 * ��������0��LED��ô,LED�����ᱻ����
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
	 * ����LED��ʾ��һ���ı�
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
	/**���ؼǷְ����
	 * 
	 * @return �Ƿְ����
	 */
	public String getTitle(){
		return objective.getDisplayName();
	}
	/**������Ȼ˳��д��String����
	 * ����LED����д��LED�ڵĵ�һ���ı�
	 * 
	 * @return �Ƿְ��ı�
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
	 * setTitle ���ñ���
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
}
