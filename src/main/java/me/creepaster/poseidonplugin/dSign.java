package me.creepaster.poseidonplugin;


import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;


public class dSign implements Listener {
@EventHandler
public void onJoin(PlayerJoinEvent event)
{
Player player = event.getPlayer();
}

@EventHandler
public void onSignBlock(SignChangeEvent e)
{
    if (e.getLine(0).equalsIgnoreCase("[Metadata]")) {
        e.setLine(0, "§9[Metadata]");
        e.getLine(1);
        e.getPlayer().sendMessage("§eSign placed detected");
        String idline = e.getLine(1);
        if ((idline.matches("^[0-9]*$") && idline.length() <= 2 && !idline.isEmpty()))
        {
            e.setLine(1, "§f"+idline);
            e.getPlayer().sendMessage("§5Sign succesfully placed.");
            e.getPlayer().sendMessage(idline);}
        else {
            e.getPlayer().sendMessage("§cError, please insert a valid metadata.");
            int x = e.getBlock().getLocation().getBlockX();
            int y = e.getBlock().getLocation().getBlockY();
            int z = e.getBlock().getLocation().getBlockZ();
            final World world = e.getBlock().getWorld();
            world.getBlockAt(x,y,z).setType(Material.AIR);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.SIGN, 1)); }



    }
        }


}


