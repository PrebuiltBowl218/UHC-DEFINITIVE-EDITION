package me.aleiv.core.paper.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.aleiv.core.paper.Core;
import me.aleiv.core.paper.Game.GameStage;
import net.md_5.bungee.api.ChatColor;

public class GracePeriodListener implements Listener {

    Core instance;

    private static String IPVP_DISABLED = ChatColor.RED + "iPvP is currently disabled.";
    private static String PVP_DISABLED = ChatColor.RED + "PvP is currently disabled.";

    public GracePeriodListener(Core instance) {
        this.instance = instance;
    }

    //PVP BOOLEAN CODE
    @EventHandler(priority = EventPriority.HIGH)
    public void onPVP(EntityDamageByEntityEvent e) {
        if (e.getEntity() == e.getDamager() || instance.getGame().isPvp()
                || instance.getGame().getGameStage().equals(GameStage.LOBBY))
            return;

        if (e.getEntity() instanceof Player) {
            Player p2 = null;
            if (e.getDamager() instanceof Player) {
                p2 = (Player) e.getDamager();
            } else if (e.getDamager() instanceof Projectile) {
                Projectile proj = (Projectile) e.getDamager();
                if (proj.getShooter() instanceof Player) {
                    p2 = (Player) proj.getShooter();
                }
            }
            if (p2 != null) {
                p2.sendMessage(PVP_DISABLED);
                e.setCancelled(true);
            }
        }
    }

    //CANCEL SPAWN EGGS
    @EventHandler(priority = EventPriority.HIGH)
    public void onHoldEgg(PlayerInteractEvent e) {
        if (instance.getGame().isPvp())
            return;
        final var player = e.getPlayer();
        final var item = player.getInventory().getItemInMainHand();
        if (item.getType().toString().contains("SPAWN")) {
            e.setCancelled(true);
            player.sendMessage(IPVP_DISABLED);
        }
    }

    //CANCEL DAMAGE TO VILLAGERS IN NO PVP
    @EventHandler
    public void onDamageVillager(EntityDamageByEntityEvent e) {
        if (!instance.getGame().isPvp() && e.getEntity() instanceof Villager) {
            e.setCancelled(true);
            if (e.getDamager() instanceof Player)
                e.getDamager().sendMessage(ChatColor.RED + "Villagers are invincible in no-pvp period.");
        }
    }

    //CANCEL DAMAGE VILLAGER
    @EventHandler
    public void onDamageVillagerV2(EntityDamageEvent e) {
        if (!instance.getGame().isPvp() && e.getEntity() instanceof Villager)
            e.setCancelled(true);
    }

    //IPVP CANCEL
    @EventHandler
    public void onFireiPvp(PlayerInteractEvent e) {
        if (instance.getGame().isPvp())
            return;
        var player = e.getPlayer();
        final var item = player.getInventory().getItemInMainHand().getType();

        var playerIPvP = player.getNearbyEntities(5, 5, 5).stream().filter(entity -> entity instanceof Player).map(ent -> (Player) ent)
            .filter( p -> p.getUniqueId() != player.getUniqueId() && p.getGameMode() == GameMode.SURVIVAL).findFirst();

            if(playerIPvP.isPresent()){

                if (item.equals(Material.FLINT_AND_STEEL) || item.equals(Material.LAVA_BUCKET)
                || item.equals(Material.FIRE_CHARGE) || item.equals(Material.TNT_MINECART)
                || item.equals(Material.ENDER_PEARL)) {
                    e.setCancelled(true);
                    player.sendMessage(IPVP_DISABLED);

                }
            }

    }

    //IPVP CANCEL
    @EventHandler
    public void onPlaceiPvp(BlockPlaceEvent e) {
        if (instance.getGame().isPvp())
            return;
        var player = e.getPlayer();
        var block = e.getBlock().getType();

        if (player.getWorld().getEnvironment() == Environment.NETHER && block.toString().contains("BED")) {
            e.setCancelled(true);
            return;
        }

        var playerIPvP = player.getNearbyEntities(5, 5, 5).stream().filter(entity -> entity instanceof Player).map(ent -> (Player) ent)
            .filter( p -> p.getUniqueId() != player.getUniqueId() && p.getGameMode() == GameMode.SURVIVAL).findFirst();

            if(playerIPvP.isPresent()){

                if(block.equals(Material.SAND) || block.equals(Material.GRAVEL) || block.toString().contains("POWDER")
                || block.toString().contains("CAMPFIRE") || block.toString().contains("ANVIL")
                || block.equals(Material.MAGMA_BLOCK) || block.equals(Material.CACTUS) || block.equals(Material.TNT)) {
                    e.setCancelled(true);
                    player.sendMessage(IPVP_DISABLED);

                }
            }

    }

    @EventHandler
    public void onBreakiPvp(BlockBreakEvent e) {
        if (instance.getGame().isPvp())
            return;
        var block = e.getBlock().getType();
        var player = e.getPlayer();
        var playerIPvP = player.getNearbyEntities(5, 5, 5).stream().filter(entity -> entity instanceof Player).map(ent -> (Player) ent)
            .filter( p -> p.getUniqueId() != player.getUniqueId() && p.getGameMode() == GameMode.SURVIVAL).findFirst();

            if(playerIPvP.isPresent()){

                if(block.equals(Material.FURNACE) || block.toString().contains("ANVIL")
                || block.toString().contains("TABLE")) {
                    e.setCancelled(true);
                    player.sendMessage(IPVP_DISABLED);

                }
            }

    }

}