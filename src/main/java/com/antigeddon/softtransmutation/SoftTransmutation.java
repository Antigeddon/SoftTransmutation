package com.antigeddon.softtransmutation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import static org.bukkit.Bukkit.getLogger;


public class SoftTransmutation extends JavaPlugin {

    private File configFile;
    private List<String> blockWhitelist = new ArrayList<>();

    @Override
    public void onEnable() {
        getServer().getLogger().info("[SoftTmtt] 1.0 initialized");

        Bukkit.getPluginManager().registerEvents(new dClick(this), this);
        Bukkit.getPluginManager().registerEvents(new dSign(this), this);

        File pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        configFile = new File(pluginFolder, "config.yml");

        if (!configFile.exists()) {
            createDefaultConfig();
        }
        loadConfig();
        checkBlockWhitelist();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String version = getDescription().getVersion();
        if (command.getName().equalsIgnoreCase("softtmtt") ||
                command.getName().equalsIgnoreCase("softtransmutation")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (blockWhitelist.isEmpty()) {
                    player.sendMessage(pName() + "§c The whitelist is empty.");
                    player.sendMessage("§7SoftTransmutation " + version);
                } else {
                    player.sendMessage(pName() + "§a The list of compatible block(s):");
                    for (String block : blockWhitelist) {
                        player.sendMessage("§f- " + block);
                    }
                    player.sendMessage("§7SoftTransmutation " + version);
                }
            } else {
                getLogger().info("[SoftTmtt] This command can only be executed by a player.");
            }
            return true;
        }
        return false;
    }

    private void createDefaultConfig() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            writer.write("block-whitelist:\n");
            writer.write("  - LONG_GRASS\n");
            writer.write("  - COBBLESTONE_STAIRS\n");
            writer.write("  - WOOD_STAIRS\n");
        } catch (IOException e) {
            getLogger().severe(e.getMessage());
        }
    }
        public String pName() {
            return "§f[§9SoftTmtt§f]";

    }
    private void loadConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            String currentKey = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("block-whitelist:")) {
                    currentKey = "block-whitelist";
                } else if (line.startsWith("- ") && currentKey.equals("block-whitelist")) {
                    String blockName = line.substring(2).trim();
                    blockWhitelist.add(blockName);
                }
            }
            getLogger().info("[SoftTmtt] whitelisted block(s):" + blockWhitelist);

        } catch (IOException e) {
            getLogger().severe(e.getMessage());
        }
    }
    public List<String> getBlockWhitelist() {
        return blockWhitelist;
    }
    private void checkBlockWhitelist() {
        for (String blockName : blockWhitelist) {
            try {
                Material material = Material.valueOf(blockName);
            } catch (IllegalArgumentException e) {
                getLogger().warning("[SoftTmtt] Invalid block name in whitelist: " + blockName);
            }
        }
    }



    @Override
    public void onDisable() {
        getServer().getLogger().info("[SoftTmtt] 1.0 is asleep ~ZZZzzzz...");

    }
}

