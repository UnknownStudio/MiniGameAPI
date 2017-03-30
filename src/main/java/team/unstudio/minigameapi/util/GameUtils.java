package team.unstudio.minigameapi.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public final class GameUtils {
	
    private static ProtocolManager pm;
	static {
		pm = ProtocolLibrary.getProtocolManager();
    }
	
	private GameUtils() {}
    
    /**
     * 使玩家恢复到初始状态
     */
    public static void resetPlayer(Player player){
        player.setHealth(20);
        player.resetMaxHealth();
        player.setFoodLevel(20);
        player.setWalkSpeed(0.2F);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.getInventory().clear();
        player.setFireTicks(0);
    }
    
	public static void sendActionBar(Player player,String text){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.CHAT);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(text));
        packet.getBytes().write(0, (byte)2);
        try {
			pm.sendServerPacket(player, packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }
	
	public static void sendTitle(Player player,String text,int fadeIn,int show,int fadeOut,boolean isSubTitle){
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText(text));
		EnumWrappers.TitleAction action = isSubTitle ? EnumWrappers.TitleAction.SUBTITLE : EnumWrappers.TitleAction.TITLE;
		packet.getTitleActions().write(0, action);
		packet.getIntegers().write(0, fadeIn);
		packet.getIntegers().write(1, show);
		packet.getIntegers().write(2, fadeOut);
		try{
			pm.sendServerPacket(player, packet);
		} catch (InvocationTargetException e){
			e.printStackTrace();
		}
	}
	
	public static void resetTitle(Player player){
		//TODO:
	}
}
