package team.unstudio.minigameapi.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import team.unstudio.minigameapi.util.GameRule;

public final class GameRuleListener implements Listener{

	@EventHandler(priority=EventPriority.HIGHEST,ignoreCancelled=true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player&&GameRule.hasRule(event.getDamager(), GameRule.NOPVP)){
			event.setCancelled(true);
			return;
		}
		
		if(event.getEntity() instanceof Player&&GameRule.hasRule(event.getEntity(), GameRule.NOPVP)){
			event.setCancelled(true);
			return;
		}
	}
}
