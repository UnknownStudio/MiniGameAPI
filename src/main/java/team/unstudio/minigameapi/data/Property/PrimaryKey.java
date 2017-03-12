package team.unstudio.minigameapi.data.TableProperty;

public class PrimaryKey implements TableProperty{
	String key;
	/**
	 * 
	 * @param key The name of key
	 */
	public PrimaryKey(String key){
		this.key = key;
	}
	@Override
	public String getCode() {
		return "PRIMARY KEY ("+this.key+")";
	}
}
