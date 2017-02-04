package team.unstudio.minigameapi.currency;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import team.unstudio.minigameapi.MiniGameAPI;

public class Currency {
	private static SaveType saveType = SaveType.PLAYER_NAME;
	private static Connection conn;
	private final static String TABLE_NAME = "MGA_ECONOMY";
	private static double defaultMoney = 0D;
	/**
	 * @param type SaveType
	 */
	
	public static void setSaveType(SaveType type){
		saveType = type;
	}
	/**
	 * 
	 * @param player
	 */
	public static void setConnection(Connection con){
		conn = con;
	}
	public static void create(OfflinePlayer player) throws SQLException{
		String name = getSaveName(player);
		PreparedStatement prepare = conn.prepareStatement("INSERT INTO ? (USER_ID, Money) VALUES (?, ?)");
		prepare.setString(1, TABLE_NAME);
		prepare.setString(2, name);
		prepare.setDouble(3, defaultMoney);
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
		PreparedStatement prepare = conn.prepareStatement("SELECT Money FROM ? WHERE USER_ID LIKE ? LIMIT 0,1");
		prepare.setString(1, TABLE_NAME);
		prepare.setString(2, name);
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
		PreparedStatement prepare = conn.prepareStatement("UPDATE ? SET Money=Money+? WHERE USER_ID LIKE ?");
		prepare.setString(1, TABLE_NAME);
		prepare.setDouble(2, value);
		prepare.setString(3, name);
		prepare.executeUpdate();
		prepare.close();
	}
	/**
	 * 
	 * @param value how much you want to set
	 */
	public static void set(OfflinePlayer player,double value) throws SQLException{
		String name = getSaveName(player);
		PreparedStatement prepare = conn.prepareStatement("UPDATE ? SET Money=? WHERE USER_ID LIKE ?");
		prepare.setString(1, TABLE_NAME);
		prepare.setDouble(2, value);
		prepare.setString(3, name);
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
		PreparedStatement prepare = conn.prepareStatement("UPDATE ? SET Money=Money-? WHERE USER_ID LIKE ?");
		prepare.setString(1, TABLE_NAME);
		prepare.setDouble(2, value);
		prepare.setString(3, name);
		prepare.executeUpdate();
		prepare.close();
	}
	private static String getSaveName(OfflinePlayer player){
		if (saveType == SaveType.PLAYER_NAME) return player.getName();
		return player.getUniqueId().toString();
	}
}
