package io.mewsub.dupeframe;

import org.bukkit.plugin.Plugin;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Server;

import io.mewsub.dupeframe.Listeners.PlayerInteractEntity;

public class DupeFrame extends JavaPlugin {

    public static Plugin plugin;
    public static Server server;

    @Override
    public void onEnable() {
        this.plugin = ( Plugin ) this;
        this.server = this.getServer();
        this.server.getPluginManager().registerEvents( new PlayerInteractEntity(), this );
    }

    @Override
    public void onDisable() {

    }

}
