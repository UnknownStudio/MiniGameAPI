package team.unstudio.minigameapi.data.TableProperty;

public class Unique implements TableProperty{
	String uniques[];
	String constraintName;
	/**
	 * 
	 * @param constraintName The name of constraint
	 * @param uniques The uniques
	 */
	public Unique(String constraintName,String... uniques){
		this.constraintName = constraintName;
		this.uniques = uniques;
	}
	@Override
	public String getCode() {
		StringBuilder sb = new StringBuilder();
		for (String unique : uniques) sb.append(unique+",");
		sb.delete(sb.length()-1, sb.length());
		return "CONSTRAINT "+this.constraintName +" UNIQUE ("+sb.toString()+")";
	}

}
