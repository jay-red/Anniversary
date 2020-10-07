package io.mewsub.anniversary.listeners;

import io.mewsub.anniversary.Anniversary;
import io.mewsub.anniversary.AnniversaryPlayer;

import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import org.bukkit.Material;

import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.spigotmc.event.entity.EntityDismountEvent;

public class EntityDismount implements Listener {
    
    @EventHandler
    public void onEntityDismount( EntityDismountEvent evt ) {
        Entity ent = evt.getEntity();
        Entity dismounted = evt.getDismounted();
        if( ent instanceof Player && dismounted	instanceof Cat ) {
        	AnniversaryPlayer player = Anniversary.getPlayer( ( Player ) ent );
        	player.setCat( null );
        }
    }

}