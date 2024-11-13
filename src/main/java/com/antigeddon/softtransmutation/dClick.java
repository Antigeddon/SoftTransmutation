package com.antigeddon.softtransmutation;

import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Sign;
import java.util.List;

public class dClick implements Listener {

    private final SoftTransmutation plugin;
    private LWC lwc;

    public dClick(SoftTransmutation plugin) {
        this.plugin = plugin;

        if (plugin.getServer().getPluginManager().getPlugin("LWC") != null) {
            this.lwc = LWC.getInstance();
            plugin.getServer().getLogger().info("[SoftTmtt] LWC " + lwc.getVersion() + " detected and initialized.");
        } else {
            plugin.getServer().getLogger().info("[SoftTmtt] LWC plugin not found. Protection handling will be skipped.");
        }
    }

    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.LEFT_CLICK_BLOCK)) {
            Material itemType = player.getItemInHand().getType();
            List<String> blockWhitelist = plugin.getBlockWhitelist();

            if (blockWhitelist.contains(itemType.toString())) {
                if (isSign(event)) {
                    Sign sign = (Sign) event.getClickedBlock().getState();

                    if (isMetaLine(event)) {
                        String idline = sign.getLine(1).replace("§f", "");

                        if (idline.matches("^[0-9]*$") && idline.length() <= 2 && !idline.isEmpty()) {

                            if (lwc != null) {
                                Protection protection = lwc.findProtection(event.getClickedBlock());

                                if (protection != null) {
                                    if (protection.isOwner(player)) {
                                        protection.remove();
                                        player.sendMessage(plugin.pName() + "§2 Removed LWC protection on the Sign successfully.");
                                    } else {
                                        return;
                                    }
                                }
                            }

                            player.sendMessage(plugin.pName()  + "§a Block successfully placed.");
                            player.getInventory().removeItem(new ItemStack(itemType, 1));

                            int idblock = Integer.parseInt(idline);
                            int x = event.getClickedBlock().getLocation().getBlockX();
                            int y = event.getClickedBlock().getLocation().getBlockY();
                            int z = event.getClickedBlock().getLocation().getBlockZ();
                            final World world = event.getClickedBlock().getWorld();
                            world.getBlockAt(x, y, z).setType(itemType);
                            world.getBlockAt(x, y, z).setData((byte) idblock);

                            event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), new ItemStack(Material.SIGN, 1));
                        } else {
                            player.sendMessage(plugin.pName()  + "§c The metadata is invalid.");
                            removeSignAndNotify(event);
                        }
                    }

                }

            } else if (player.getItemInHand().getType() == Material.AIR) {
                    if (isSign(event) && isMetaLine(event)) {
                            player.sendMessage(plugin.pName() + "§c You have to hold a block.");
                    }
                    }
                    }

    }
        //It's not meant to be used, it's a safety feature to prevent potential exploits//
    private void removeSignAndNotify(PlayerInteractEvent event) {
        World world = event.getClickedBlock().getWorld();
        int x = event.getClickedBlock().getLocation().getBlockX();
        int y = event.getClickedBlock().getLocation().getBlockY();
        int z = event.getClickedBlock().getLocation().getBlockZ();
        world.getBlockAt(x, y, z).setType(Material.AIR);
        world.dropItemNaturally(event.getClickedBlock().getLocation(), new ItemStack(Material.SIGN, 1));
        //It's not meant to be used, it's a safety feature to prevent potential exploits//
    }
    private boolean isSign(PlayerInteractEvent event) {
        Material blockType = event.getClickedBlock().getType();
        return blockType == Material.WALL_SIGN || blockType == Material.SIGN_POST;
    }
        private boolean isMetaLine(PlayerInteractEvent event) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            return sign.getLine(0).equalsIgnoreCase("§9[Metadata]");

        }
}




