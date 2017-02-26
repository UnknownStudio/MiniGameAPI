package team.unstudio.minigameapi.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class LoopBoard extends Board{
	private List<String> text = new ArrayList<>();
	private int index =0;
	private final int line;
	public LoopBoard(String title,int line) {
		super(title);
		this.line=line;
	}
	public LoopBoard(String title,int line,String[] text){
		super(title);
		this.line = line;
		setLoopDisplay(text);
	}
	public LoopBoard(String title,int line,List<String> text){
		super(title);
		this.line = line;
		setLoopDisplay(text);
	}
	public void setLoopDisplay(List<String> c){
		this.text =c;
		showNext();
	}
	public void setLoopDisplay(String[] text){
		ArrayList<String> a = new ArrayList<>();
		for(int i=0;i<text.length;i++)
			if(text[i]!=null)
				a.add(text[i]);
		setLoopDisplay(a);
	}
	public void removeLoopDisplay(int line){
		super.remove(line);
	}
	public void showNext(){
		if(index>=text.size())index=0;
		super.set(line, text.get(index));
		index++;
	}
	public int getLine(){
		return line;
	}
	@Override
	public String[] getText(){
		return text.toArray(new String[0]);
	}
	@Override
	public int hashCode(){
		return line+text.hashCode();
	}
	@Override
	public boolean equals(Object o){
		if(o==null) throw new NullPointerException();
		if(!(o instanceof LoopBoard))return false;
		LoopBoard lb = (LoopBoard) o;
		if(line != lb.getLine()) return false;
		if(!text.toArray(new String[0]).equals(lb.getText()))return false;
		if(!(super.getIdle().equals(lb.getIdle())))return false;
		return true;
	}
}
