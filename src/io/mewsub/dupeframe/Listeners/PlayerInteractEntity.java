package io.mewsub.dupeframe.Listeners;

import org.bukkit.entity.Entity;
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
        Player player = evt.getPlayer();
        Entity e = evt.getRightClicked();
        if( e instanceof ItemFrame ) {
            ItemFrame frame = ( ItemFrame ) e;
            ItemStack item = frame.getItem();
            ItemStack pItem = player.getInventory().getItemInMainHand();
            if( pItem != null && item != null ) {
                if( pItem.getType() == item.getType() && pItem.getType() != Material.AIR ) {
                    ItemMeta pMeta = pItem.getItemMeta();
                    ItemMeta meta = item.getItemMeta();
                    if( pMeta.getDisplayName().equals( meta.getDisplayName() ) ) {
                        ItemStack duped = new ItemStack( pItem );
                        e.getWorld().dropItemNaturally( e.getLocation(), duped );
                    }
                }
            }
        }
    }

}