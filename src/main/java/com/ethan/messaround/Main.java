package com.ethan.messaround;

import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new PassengerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChickenFlyingEvents(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
