package team.unstudio.minigameapi.gamecoin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.OfflinePlayer;

public class GameCoin {
	private static SaveType saveType = SaveType.NAME;
	private static Connection conn;
	private final static String TABLE_NAME = "MGA_GAME_COIN";
	private static double defaultMoney = 0D;
	
	/**
	 * @param type SaveType
	 */
	public static void setSaveType(SaveType type){
		saveType = type;
	}
	
	/**
	 * 
	 * @param con connection
	 */
	public static void setConnection(Connection con){
		conn = con;
	}
	
	/**
	 * 
	 * @param player
	 */
	public static void create(OfflinePlayer player) throws SQLException{
		String name = getSaveName(player);
		PreparedStatement prepare = conn.prepareStatement("INSERT INTO "+TABLE_NAME+" (USER_ID, Money) VALUES (?, ?)");
		prepare.setString(1, name);
		prepare.setDouble(2, defaultMoney);
		prepare.executeUpdate();
		prepare.close();
	}
	
	/**
	 * 
	 * @return player's money
	 * @throws SQLException 
	 */
	public static double get(OfflinePlayer player) throws SQLException{
		String name = getSaveName(player);
		PreparedStatement prepare = conn.prepareStatement("SELECT Money FROM "+TABLE_NAME+" WHERE USER_ID LIKE ? LIMIT 0,1");
		prepare.setString(1, name);
		ResultSet result = prepare.executeQuery();
		double money = defaultMoney;
		if (result.next()) money = result.getDouble(1); 
		  else create(player);
		result.close();
		prepare.close();
		return money;
		
	}
	
	/**
	 * 
	 * @param value how much you want to give
	 */
	public static void give(OfflinePlayer player,double value) throws SQLException{
		String name = getSaveName(player);
		PreparedStatement prepare = conn.prepareStatement("UPDATE "+TABLE_NAME+" SET Money=Money+? WHERE USER_ID LIKE ?");
		prepare.setDouble(1, value);
		prepare.setString(2, name);
		prepare.executeUpdate();
		prepare.close();
	}
	
	/**
	 * 
	 * @param value how much you want to set
	 */
	public static void set(OfflinePlayer player,double value) throws SQLException{
		String name = getSaveName(player);
		PreparedStatement prepare = conn.prepareStatement("UPDATE "+TABLE_NAME+" SET Money=? WHERE USER_ID LIKE ?");
		prepare.setDouble(1, value);
		prepare.setString(2, name);
		prepare.executeUpdate();
		prepare.close();
	}
	
	/**
	 * 
	 * @param value how much you want to take
	 */
	public static void take(OfflinePlayer player,double value) throws NoEnoughMoneyException,SQLException{
		String name = getSaveName(player);
		if (get(player)-value<0) throw new NoEnoughMoneyException("Player Has No Enough Money");
		PreparedStatement prepare = conn.prepareStatement("UPDATE "+TABLE_NAME+" SET Money=Money-? WHERE USER_ID LIKE ?");
		prepare.setDouble(1, value);
		prepare.setString(2, name);
		prepare.executeUpdate();
		prepare.close();
	}
	
	private static String getSaveName(OfflinePlayer player){
		if (saveType == SaveType.NAME) return player.getName();
		return player.getUniqueId().toString();
	}
}
