package me.creepaster.poseidonplugin;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Sign;

public class dClick implements Listener  {

    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.LEFT_CLICK_BLOCK)) {

            if (player.getItemInHand().getType() == Material.LONG_GRASS) {

                if (event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN_POST) {

                    Sign sign = (Sign) event.getClickedBlock().getState();

                    if (sign.getLine(0).equalsIgnoreCase("§9[Metadata]")) {
                        sign.getLine(1);

                    player.sendMessage("You have right click a slime ball!");
                    String idline = sign.getLine(1);
                    idline = idline.replace("§f", "");
                        if ((idline.matches("^[0-9]*$") && idline.length() <= 2 && !idline.isEmpty()))
                        {
                            event.getPlayer().sendMessage("§aBlock succesfully placed.");
                            event.getPlayer().sendMessage(idline);
                            player.getInventory().contains(Material.LONG_GRASS);
                            player.getInventory().removeItem(new ItemStack(Material.LONG_GRASS, 1));
                            int idblock = Integer.parseInt(idline);
                            int x = event.getClickedBlock().getLocation().getBlockX();
                            int y = event.getClickedBlock().getLocation().getBlockY();
                            int z = event.getClickedBlock().getLocation().getBlockZ();
                            final World world = event.getClickedBlock().getWorld();
                            world.getBlockAt(x,y,z).setType(Material.LONG_GRASS);
                            world.getBlockAt(x,y,z).setData((byte) idblock);
                            event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), new ItemStack(Material.SIGN, 1)); }
                        else {
                            event.getPlayer().sendMessage("§cError, please insert a valid metadata.");
                            int x = event.getClickedBlock().getLocation().getBlockX();
                            int y = event.getClickedBlock().getLocation().getBlockY();
                            int z = event.getClickedBlock().getLocation().getBlockZ();
                            final World world = event.getClickedBlock().getWorld();
                            world.getBlockAt(x,y,z).setType(Material.AIR);
                            event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), new ItemStack(Material.SIGN, 1)); }



                    }
                }


            }
        }
    }
}











