package team.unstudio.minigameapi.currency;

import java.io.File;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import team.unstudio.minigameapi.MiniGameAPI;

public class Currency {
	private static boolean useSQL = false;
	private static String SQLHost;
	private static String SQLPort;
	private static String SQLUser;
	private static String SQLPassword;
	private static SaveType saveType = SaveType.PLAYER_NAME_INGORE_CASE;
	public static void setupSQL(String host,String port,String user,String password){
		SQLHost = host;
		SQLPort = port;
		SQLUser = user;
		SQLPassword = password;
		useSQL = true;	
	}
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
	public static void create(OfflinePlayer player){
		String name = getSaveName(player);
		if (useSQL){
			/*
			 * this add SQL code;
			 */
			return;
		}
		
	}
	/**
	 * 
	 * @return player's money
	 */
	public static double get(OfflinePlayer player){
		String name = getSaveName(player);
		if (useSQL){
			/*
			 * this add SQL code;
			 */
			return 2333D;
		}
		return 2333D;
	}
	/**
	 * 
	 * @param value how much you want to give
	 */
	public static void give(OfflinePlayer player,double value){
		String name = getSaveName(player);
		if (useSQL){
			/*
			 * this add SQL code
			 */
			return;
		}
	}
	/**
	 * 
	 * @param value how much you want to set
	 */
	public static void set(OfflinePlayer player,double value){
		String name = getSaveName(player);
		if (useSQL){
			/*
			 * this add SQL code
			 */
			return;
		}
	}
	/**
	 * 
	 * @param value how much you want to take
	 */
	public static void take(OfflinePlayer player,double value) throws NoEnoughMoneyException{
		String name = getSaveName(player);
		if (useSQL){
			/*
			 * this add SQL code
			 */
		}
	}
	private static String getSaveName(OfflinePlayer player){
		if (saveType == SaveType.PLAYER_NAME) return player.getName();
		if (saveType == SaveType.PLAYER_NAME_INGORE_CASE) return player.getName().toLowerCase();
		return player.getUniqueId().toString();
	}
}
