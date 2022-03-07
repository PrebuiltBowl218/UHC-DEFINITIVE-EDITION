package me.aleiv.core.paper.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.aleiv.core.paper.Core;
import net.md_5.bungee.api.ChatColor;

public class InGameListener implements Listener {

    Core instance;

    public InGameListener(Core instance){
        this.instance = instance;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        // 3-second timeout to get respawned in spectator mode.
        var p = e.getEntity();

        p.getActivePotionEffects().forEach(all -> p.removePotionEffect(all.getType()));
        p.setHealth(20);
        p.setGameMode(GameMode.SPECTATOR);

        p.getWorld().strikeLightningEffect(p.getLocation());
        p.sendTitle("", ChatColor.DARK_RED + "YOU ARE DEAD", 0, 100, 0);
        p.playSound(p.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.VOICE, 1.0f, 0.1f);

        /*var inv = p.getInventory().getContents();
        UHCPlayer uhcPlayer = instance.getPlayerManager().getPlayer(p.getUniqueId());
        if (uhcPlayer != null) {
            if (uhcPlayer.isAlive()) {
                uhcPlayer.setAlive(false);
                uhcPlayer.setDead(true);
                uhcPlayer.setLastKnownHealth(0.0);
                uhcPlayer.setLastKnownPositionFromLoc(p.getLocation());
                uhcPlayer.setLastKnownInventory(inv);

            }
        }
        if (p.getKiller() != null) {
            var killer = p.getKiller();
        }*/

    }

    
    @EventHandler
    public void onJoinInNether(PlayerJoinEvent e) {
        if (!instance.getGame().isNether()) {// If nether is disabled
            var player = e.getPlayer();
            //TODO: NETHER SAVE
            // If player's world is nether, scatter them in the overworld.
            if (player.getWorld().getEnvironment() == Environment.NETHER) {
                //var worldToTeleport = Bukkit.getWorld("world");
                //var radius = (int) worldToTeleport.getWorldBorder().getSize() / 2;
                // Teleport Async to save resources.
                //player.teleportAsync(ChunksManager.centerLocation(ChunksManager.findScatterLocation(worldToTeleport, radius)));
            }
        }
    }
}
