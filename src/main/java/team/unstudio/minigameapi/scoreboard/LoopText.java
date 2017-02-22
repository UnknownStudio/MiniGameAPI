package team.unstudio.minigameapi.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class LoopText implements Comparable<LoopText>{
	private List<String> text = new ArrayList<>();
	private int index =0;
	private final int line;
	public LoopText(int line){
		this.line = line;
	}
	public LoopText(int line,String[] text){
		this.line = line;
		setText(text);
	}
	public LoopText(int line,List<String> text){
		this.line = line;
		setText(text);
	}
	public void setText(String[] text){
		ArrayList<String> a = new ArrayList<>();
		for(int i=0;i<text.length;i++)
			a.add(text[i]);
		setText(a);
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
		return line+text.hashCode();
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
