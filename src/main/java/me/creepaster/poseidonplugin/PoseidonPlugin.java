package me.creepaster.poseidonplugin;


import me.creepaster.poseidonplugin.config.dConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class PoseidonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new dClick(), this);
        Bukkit.getPluginManager().registerEvents(new dSign(), this);
        }

    @Override
    public void onDisable() {

    }
}

