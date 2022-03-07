package me.aleiv.core.paper.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;
import org.bukkit.inventory.ItemStack;

import me.aleiv.core.paper.Core;
import net.md_5.bungee.api.ChatColor;

public class ConfigListener implements Listener{
    
    Core instance;

    public ConfigListener(Core instance){
        this.instance = instance;
    }

    //NETHER
    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {
        if (!instance.getGame().isNether() && e.getReason() == CreateReason.FIRE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onNetherJoin(PlayerPortalEvent e){
        var player = e.getPlayer();
        if (!instance.getGame().isNether() && player.getGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(true);
        }
    }


    //BEDS
    @EventHandler
    public void beds(BlockPlaceEvent e) {
        if (!instance.getGame().isBeds() && e.getBlock().getType().toString().contains("BED")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Beds are disabled.");
        }
    }

    //APPLE RATE
    @EventHandler
    public void onLeaf(LeavesDecayEvent e) {
        var block = e.getBlock();
        if (Math.random() <= (instance.getGame().getAppleRate() / 100)) {
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
        }

    }

    //FLINT RATE
    @EventHandler
    public void onFlint(BlockBreakEvent e) {
        var block = e.getBlock();
        if (block.getType() == Material.GRAVEL && Math.random() <= (instance.getGame().getFlintRate() / 100)) {
            e.setDropItems(false);
            block.getWorld().dropItemNaturally(block.getLocation(),
                    new ItemStack(Material.FLINT));
        }

    }

    
    
    //BED NERF

    @EventHandler
    public void beds(PlayerBedEnterEvent e) {
        var bed = e.getBed();
        
        if(bed.getWorld().getEnvironment() == Environment.NETHER){
            var game = instance.getGame();
            var player = e.getPlayer();
            if(!game.isBeds()){
                e.setCancelled(true);
                e.setUseBed(Result.DENY);
                player.sendMessage(ChatColor.RED + "Beds are disabled.");
    
            }else if (game.isBedsNerf()) {
                e.setCancelled(true);
                e.setUseBed(Result.DENY);
                bed.setType(Material.AIR);
                var loc = bed.getLocation();
                loc.getWorld().createExplosion(loc, 2.0f, true, true);

            }
        }

    }

    //TEARS
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e) {
        var stack = e.getEntity().getItemStack();
        var type = stack.getType();
        if (!instance.getGame().isTears() && type == Material.GHAST_TEAR){
            stack.setType(Material.GOLD_INGOT);
        }
    }

}
