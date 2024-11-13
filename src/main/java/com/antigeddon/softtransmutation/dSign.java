package com.antigeddon.softtransmutation;

import com.griefcraft.model.Protection;
import com.griefcraft.lwc.LWC;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;


public class dSign implements Listener {

    private final SoftTransmutation plugin;
    private LWC lwc;

    public dSign(SoftTransmutation plugin) {
        this.plugin = plugin;

        if (plugin.getServer().getPluginManager().getPlugin("LWC") != null) {
            this.lwc = LWC.getInstance();
        }
        }
@EventHandler
public void onJoin(PlayerJoinEvent event)
{
Player player = event.getPlayer();
}

@EventHandler
public void onSignBlock(SignChangeEvent e)
{
    if (e.getLine(0).equalsIgnoreCase("[Metadata]") || e.getLine(0).equalsIgnoreCase("[Meta]") || e.getLine(0).equalsIgnoreCase("[Data]")) {
        e.setLine(0, "§9[Metadata]");
        e.getLine(1);
        String idline = e.getLine(1);
        if ((idline.matches("^[0-9]*$") && idline.length() <= 2 && !idline.isEmpty()))
        {
            e.setLine(1, "§f"+idline);
            e.getPlayer().sendMessage(plugin.pName() + "§a Metadata Sign successfully placed.");
            if (idline.equals("69")) {
            e.getPlayer().sendMessage(plugin.pName() + "§d Nice.");
        }
        } else {
            if (lwc != null) {
                Protection protection = lwc.findProtection(e.getBlock());

                if (protection != null) {
                    Player player = e.getPlayer();
                    if (protection.isOwner(player)) {
                        protection.remove();
                        player.sendMessage(plugin.pName() + "§2 Removed LWC protection on the Sign successfully.");
                    } else {
                        return;
                    }
                }
            }
            e.getPlayer().sendMessage(plugin.pName() + "§c The metadata is invalid.");
            int x = e.getBlock().getLocation().getBlockX();
            int y = e.getBlock().getLocation().getBlockY();
            int z = e.getBlock().getLocation().getBlockZ();
            final World world = e.getBlock().getWorld();
            world.getBlockAt(x,y,z).setType(Material.AIR);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.SIGN, 1)); }



    }
        }


}


