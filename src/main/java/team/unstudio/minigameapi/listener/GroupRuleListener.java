package team.unstudio.minigameapi.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.entity.Player;
import team.unstudio.minigameapi.util.GroupRule;

public final class GroupRuleListener implements Listener{
    
    @EventHandler(priority=EventPriority.HIGHEST,ignoreCancelled=true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(GroupRule.containSameGroupAndHasRule(event.getDamager(),event.getEntity(),GroupRule.NO_MATE_DAMAGE))
            event.setCancelled(true);
	}
}
