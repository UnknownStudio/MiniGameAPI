package team.unstudio.minigameapi.scoreboard;

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
 * ��Ϊ���ܵİ�ȫ���⣬��ֹʹ��16�����ϵ�����
 * 
 * @author defoli_ation
 *
 */
public class Board {
	private final org.bukkit.scoreboard.Scoreboard scoreboard;
	private final Objective objective;
	private final TreeMap<Integer,String> map = new TreeMap<>();
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
	 * 
	 * ���Ľ�������ͬ������Ϸ��<br>
	 * ����֮ǰ���������ռ�ã���Ḳ�ǵ�
	 * @param line ���õ�����
	 * @param text ��Ҫ���õ�����
	 */
	public void set(int line,String text){
		if(map.containsKey(line))
			remove(line);
		map.put(line, text);
		setup();
	}
	/**
	 * �Ƴ���Ӧ����������.���Ľ�������ͬ������Ϸ��
	 * @param line ����
	 * 
	 */
	public void remove(int line){
		if(line>=16)return;
		if(map.containsKey(line)){
			this.scoreboard.resetScores(map.get(line));
			map.remove(line);
		}
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
				remove(i);
	}
	/**��������齫�ᰴ�ճ���˳��ԭ����.
	 * ����
	 * ["one","two","three"]
	 * ��Ӧ����0��one ,����1 two,����2 three<br>
	 * 
	 * ��������0��scrollDisplay��ô,scrollDisplay�����ᱻ����<br>
	 * ���Ľ�������ͬ������Ϸ��,���Array[1]Ϊ�գ���ô���н�����
	 * 
	 * @param text ׼����ʾ������
	 */
	public void reset(String[] text){
		for(int i=0;i<16;i++)
			if(text[i]!=null)
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
		for(int i=0;i<16;i++){
			String s = map.get(i);
			if(s==null)break;
			this.score=this.objective.getScore(s);
			this.score.setScore(1);
			this.score.setScore(i);;
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
		String[] s = new String[16];
		for(int i=0;i<16;i++){
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
		int[] value = new int[16];
		for(int i=0;i<16;i++){
			if(map.get(i)!=null)
				value[i] = i;
			else value[i] =-1;
		}
		return value;
	}
	@Override
	public int hashCode(){
		return map.hashCode()+objective.getDisplayName().hashCode();
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
