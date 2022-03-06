package me.aleiv.core.paper.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Egg;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.aleiv.core.paper.Core;
import me.aleiv.core.paper.Game.GameStage;
import me.aleiv.core.paper.events.GameTickEvent;
import me.aleiv.core.paper.events.NetherChangeEvent;
import net.md_5.bungee.api.ChatColor;

public class GlobalListener implements Listener{
    
    Core instance;

    public GlobalListener(Core instance){
        this.instance = instance;
    }

    @EventHandler
    public void gameTickEvent(GameTickEvent e) {
        var game = instance.getGame();
        Bukkit.getScheduler().runTask(instance, () -> {

            var timer = game.getTimer();
            if (timer.isActive()) {
                var currentTime = (int) game.getGameTime();
                timer.refreshTime(currentTime);
            }

        });

    }

    @EventHandler
    public void onShieldBreak(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            var victim = (Player) e.getEntity();
            var player = (Player) e.getDamager();
            if (victim.isBlocking() && isAxe(player.getInventory().getItemInMainHand())) {
                player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.0f, 1.0f);
            }

        }
    }

    private boolean isAxe(ItemStack e) {
        return e != null && e.getType().toString().contains("_AXE");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onArrow(EntityDamageByEntityEvent e) {
        var damager = e.getDamager();
        if (damager instanceof Trident || damager instanceof Egg || damager instanceof FishHook
                || damager instanceof Snowball || !(damager instanceof Projectile))
            return;

        if (!(((Projectile) e.getDamager()).getShooter() instanceof Player))
            return;
        if (!(e.getEntity() instanceof Player))
            return;

        Player shooter = ((Player) ((Projectile) e.getDamager()).getShooter());

        Player p = (Player) e.getEntity();

        if (p.getHealth() - e.getFinalDamage() <= 0.0D || p.isBlocking())
            return;

        if (shooter == p)
            return;

        shooter.sendMessage(ChatColor.of("#e0e0e0") + p.getDisplayName() + " is at " + ChatColor.WHITE
                + (((int) (p.getHealth() - e.getFinalDamage())) / 2.0D) + ChatColor.of("#fa0f0f") + "❤");

    }

    public void joinMessage(PlayerJoinEvent e) {
        e.setJoinMessage("");

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
    }

    /*
     * Nether disabled
     */
    @EventHandler
    public void onNetherChange(NetherChangeEvent e) {
        var game = instance.getGame();
        if (game.getGameStage() == GameStage.INGAME) {

            if(!game.isNether()){
                //TODO: TELEPORT EVERYONE IN NETHER TO OVERWORLD
            }

        }

    }

    
}
