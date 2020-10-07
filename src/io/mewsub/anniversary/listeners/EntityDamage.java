package io.mewsub.anniversary.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
    
    @EventHandler
    public void onEntityDamage( EntityDamageEvent evt ) {
    	Entity ent = evt.getEntity();
    	boolean neglect = false;
    	if( ent instanceof Player ) evt.setCancelled( true );
    	if( ent instanceof Giant ) {
			double dmg = evt.getDamage();
			switch( evt.getCause() ) {
				case FALL:
					dmg = dmg / 6;
					dmg -= 3;
					if( dmg <= 0 ) {
						evt.setCancelled( true );
						return;
					}
					break;
			}
			evt.setDamage( dmg );
    	}
    }

}