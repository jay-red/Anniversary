package io.mewsub.anniversary;

import org.bukkit.plugin.Plugin;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Server;

import io.mewsub.anniversary.commands.AnniversaryCommands;

import io.mewsub.anniversary.listeners.PlayerInteractEntity;

public class Anniversary extends JavaPlugin {

    public static Plugin plugin;
    public static Server server;

    @Override
    public void onEnable() {
        Anniversary.plugin = ( Plugin ) this;
        Anniversary.server = this.getServer();
        Anniversary.server.getPluginManager().registerEvents( new PlayerInteractEntity(), this );
        this.getCommand( "anniversary" ).setExecutor( new AnniversaryCommands() );
    }

    @Override
    public void onDisable() {

    }

}
