package io.mewsub.anniversary;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Server;

import io.netty.channel.ChannelPipeline;

import org.bukkit.entity.Player;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;

import io.mewsub.anniversary.commands.AnniversaryCommands;

import io.mewsub.anniversary.listeners.EntityDamage;
import io.mewsub.anniversary.listeners.EntityDismount;
import io.mewsub.anniversary.listeners.PlayerInteract;
import io.mewsub.anniversary.listeners.PlayerInteractEntity;

public class Anniversary extends JavaPlugin {

    public static Plugin plugin;
    public static Server server;

    public static Map<UUID, AnniversaryPlayer> players;

    public static AnniversaryPlayer getPlayer( Player player ) {
        UUID uuid = player.getUniqueId();
        AnniversaryPlayer ap = Anniversary.players.get( uuid );
        if( ap == null ) {
            ap = new AnniversaryPlayer( player );
            Anniversary.players.put( uuid, ap );
        }
        return ap;
    }

    @Override
    public void onEnable() {
        Anniversary.plugin = ( Plugin ) this;
        Anniversary.server = this.getServer();
        Anniversary.server.getPluginManager().registerEvents( new EntityDamage(), this );
        Anniversary.server.getPluginManager().registerEvents( new EntityDismount(), this );
        Anniversary.server.getPluginManager().registerEvents( new PlayerInteract(), this );
        Anniversary.server.getPluginManager().registerEvents( new PlayerInteractEntity(), this );
        this.getCommand( "anniversary" ).setExecutor( new AnniversaryCommands() );
        Anniversary.players = new HashMap<UUID, AnniversaryPlayer>();
        for( Player p : Anniversary.server.getOnlinePlayers() ) {
            Anniversary.getPlayer( p );
            ( ( CraftPlayer ) p ).getHandle().playerConnection.networkManager.channel.pipeline().addBefore( "packet_handler", "anniversary_handler", new AnniversaryDuplex( p ) );
        }
    }

    @Override
    public void onDisable() {
        Anniversary.players = null;
        ChannelPipeline pipeline;
        for( Player p : Anniversary.server.getOnlinePlayers() ) {
            pipeline = ( ( CraftPlayer ) p ).getHandle().playerConnection.networkManager.channel.pipeline();
            if( pipeline.context( "anniversary_handler" ) == null ) continue;
            pipeline.remove( "anniversary_handler" );
        }
    }

}
