package io.mewsub.anniversary.listeners;

import io.mewsub.anniversary.Anniversary;
import io.mewsub.anniversary.AnniversaryPlayer;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import org.bukkit.Material;

import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {
    
    @EventHandler
    public void onPlayerInteractEntity( PlayerInteractEntityEvent evt ) {
        Entity ent = evt.getRightClicked();
        if( ent instanceof Cat ) {
        	Cat cat = ( Cat ) ent;
        	Player p = evt.getPlayer();
        	ent.addPassenger( p );
        	AnniversaryPlayer player = Anniversary.getPlayer( p );
        	player.setCat( cat );
        } else if( ent instanceof Giant ) {
            Giant giant = ( Giant ) ent;
            ArmorStand stand = ( ArmorStand ) giant.getWorld().spawnEntity( giant.getEyeLocation(), EntityType.ARMOR_STAND );
            stand.setInvulnerable( true );
            stand.setVisible( false );
            ent.addPassenger( stand );
            Player p = evt.getPlayer();
            stand.addPassenger( p );
            AnniversaryPlayer player = Anniversary.getPlayer( p );
            player.setGiant( giant );
        }  else if( ent instanceof ArmorStand ) {
            ent.addPassenger( evt.getPlayer() );
        }
    }
}