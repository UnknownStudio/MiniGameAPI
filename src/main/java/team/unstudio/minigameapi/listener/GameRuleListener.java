package team.unstudio.minigameapi.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import team.unstudio.minigameapi.util.GameRule;

public final class GameRuleListener implements Listener{

	@EventHandler(priority=EventPriority.HIGHEST,ignoreCancelled=true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player&&event.getEntity() instanceof Player&&(
				GameRule.hasRule(event.getDamager(), GameRule.NOPVP)||
				GameRule.hasRule(event.getEntity(), GameRule.NOPVP))){
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST,ignoreCancelled=true)
	public void onPlayerMove(PlayerMoveEvent event){
		Location from = event.getFrom();
		Location to = event.getTo();
		if(GameRule.hasRule(event.getPlayer(), GameRule.NOMOVE)&&
				(from.getX()!=to.getX()||
				from.getY()!=to.getY()||
				from.getZ()!=to.getZ()))
			event.setCancelled(true);
	}
}
