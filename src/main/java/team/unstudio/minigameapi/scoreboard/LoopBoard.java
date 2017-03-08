package team.unstudio.minigameapi.scoreboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class LoopBoard extends Board{
	private final TreeSet<LoopText> text =new TreeSet<>();
	public LoopBoard(String title,int line,String[] text){
		super(title);
		setLoopDisplay(line,text);
	}
	public LoopBoard(String title,int line,List<String> text){
		super(title);
		setLoopDisplay(line,text);
	}
	public void setLoopDisplay(int line,List<String> text){
		this.text.add(new LoopText(line,text));
		showNext();
	}
	public void setLoopDisplay(int line,String[] text){
		setLoopDisplay(line,getText(text));
	}
	public boolean removeLoopDisplay(int line){
		Iterator<LoopText> it = text.iterator();
		while(it.hasNext()){
			LoopText lt = it.next();
			if(lt.getLine()==line){
				super.remove(line);
				text.remove(lt);
				return true;
			}
		}
		return false;
	}
	public boolean removeLoopDisplay(List<String> text){
		Iterator<LoopText> it = this.text.iterator();
		while(it.hasNext()){
			LoopText lt = it.next();
			if(lt.getText().equals(text)){
				lt.next();
				super.remove(lt.last());
				text.remove(lt);
				return true;
			}
		}
		return false;
	}
	public boolean removeLoopDisplay(String[] text){
		return removeLoopDisplay(getText(text));
	}
	private List<String> getText(String[] text){
		ArrayList<String> a = new ArrayList<>();
		for(int i=0;i<text.length;i++)
			if(text[i]!=null)
				a.add(text[i]);
		return a;
	}
	public void showNext(){
		Iterator<LoopText> it = text.iterator();
		while(it.hasNext()){
			LoopText l = it.next();
			super.scoreboard.resetScores(l.last());
			super.set(l.getLine(),l.next());
		}
	}
	@Override
	public int hashCode(){
		return text.hashCode();
	}
	@Override
	public boolean equals(Object o){
		if(o==null) throw new NullPointerException();
		if(!(o instanceof LoopBoard))return false;
		LoopBoard lb = (LoopBoard) o;
		if(!text.toArray(new String[0]).equals(lb.getText()))return false;
		if(!(super.getIdle().equals(lb.getIdle())))return false;
		return true;
	}
	private class LoopText implements Comparable<LoopText>{
		private List<String> text = new ArrayList<>();
		private int index =0;
		private final int line;
		public LoopText(int line,List<String> text){
			this.line = line;
			setText(text);
		}
		public void setText(List<String> text){
			this.text=text;
		}
		public String next(){
			if(index>=text.size())index=0;
			String s = text.get(index);
			index++;
			return s;
		}
		public int getLine(){
			return line;
		}
		public List<String> getText(){
			return text;
		}
		public String last(){
			if(index==text.size())return text.get(text.size()-1);
			if(index==0)return text.get(text.size()-1);
			return text.get(index-1);
		}
		public String first(){
			return text.get(0);
		}
		@Override
		public int hashCode(){
			return line*7+text.hashCode();
		}
		@Override
		public boolean equals(Object o){
			if(o==null)throw new NullPointerException();
			if(!(o instanceof LoopText))return false;
			LoopText lt = (LoopText) o;
			if(line != lt.getLine())return false;
			if(!text.get(0).equals(lt.first()))return false;
			if(!text.equals(lt.getText()))return false;
			return true;
		}
		@Override
		public int compareTo(LoopText arg0) {
			return line-arg0.getLine();
		}
	}
}
