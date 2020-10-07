package io.mewsub.anniversary.listeners;

import io.mewsub.anniversary.Anniversary;

import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.block.Block;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;

import org.bukkit.event.block.Action;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    
    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent evt ) {
        switch( evt.getAction() ) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                Giant giant = Anniversary.getPlayer( evt.getPlayer() ).getGiant();
                Player player = evt.getPlayer();
                if( Anniversary.getPlayer( player ).getGiant() != null ) {
                    giant.swingMainHand();
                    Location loc = player.getEyeLocation();
                    for( Entity entity : player.getNearbyEntities( 48, 48, 48 ) ) {
                        if( entity == giant ) continue;
                        if( ( entity instanceof Damageable ) && player.hasLineOfSight( entity ) ) {
                            if( entity.getBoundingBox().rayTrace( loc.toVector(), loc.getDirection(), 48 ) != null ) {
                                entity.setFireTicks( 10 );
                                ( ( Damageable ) entity ).damage( 100, player );
                            }
                        }
                    }
                }
                return;
        }
    }

}