package me.aleiv.core.paper.listeners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Drowned;
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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Getter;
import me.aleiv.core.paper.Core;
import net.md_5.bungee.api.ChatColor;

public class DefaultListener implements Listener{
    
    Core instance;

    Random random = new Random();
    private @Getter List<Material> possibleFence = Arrays.stream(Material.values())
            .filter(material -> material.name().contains("FENCE") && !material.name().contains("FENCE_GATE"))
            .collect(Collectors.toList());

    public DefaultListener(Core instance){
        this.instance = instance;

    }

    //FENCE HEAD

    @EventHandler
    public void onDeathHead(PlayerDeathEvent e) {
        final Player p = e.getEntity();

        p.getLocation().getBlock().setType(getRandomFence());

        Block head = p.getLocation().getBlock().getRelative(BlockFace.UP);
        head.setType(Material.PLAYER_HEAD);

        if (head.getState() instanceof Skull) {
            Skull skull = (Skull) head.getState();
            skull.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
            skull.update();
        }
    }

    private Material getRandomFence() {
        return possibleFence.get(new Random().nextInt(possibleFence.size()));
    }
    
    //INCREASED TRIDENT DROP START

    @EventHandler
    public void onDrownedDeath(EntityDeathEvent e) {
        var entity = e.getEntity();

        if(entity instanceof Drowned drowned){
            var equipment = drowned.getEquipment();
            var mainHand = equipment.getItemInMainHand().clone();
            var offHand = equipment.getItemInOffHand().clone();
            equipment.clear();
            
            if (mainHand.getType() != Material.AIR) {
                if (mainHand.getType() == Material.TRIDENT) {
                    Damageable dmg = (Damageable) mainHand.getItemMeta();
                    dmg.setDamage(random.nextInt(125));
                    mainHand.setItemMeta((ItemMeta) dmg);
    
                }
                drowned.getLocation().getWorld().dropItemNaturally(drowned.getLocation(), mainHand);
    
            }
            if (offHand.getType() != Material.AIR) {
                if (offHand.getType() == Material.TRIDENT) {
                    Damageable dmg = (Damageable) offHand.getItemMeta();
                    dmg.setDamage(random.nextInt(125));
                    offHand.setItemMeta((ItemMeta) dmg);
                }
                drowned.getLocation().getWorld().dropItemNaturally(drowned.getLocation(), offHand);
            }
        }
        
    }

    //SHIELD SOUND
    @EventHandler
    public void onShieldBreak(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player player && e.getEntity() instanceof Player victim) {

            if (victim.isBlocking() && isAxe(player.getInventory().getItemInMainHand())) {
                player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.0f, 1.0f);
            }

        }
    }

    private boolean isAxe(ItemStack e) {
        return e != null && e.getType().toString().contains("_AXE");
    }

    
    //HEALTH DAMAGE
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onArrow(EntityDamageByEntityEvent e) {

        if(e.getDamager() instanceof Projectile damager && e.getEntity() instanceof Player player){
            
            if (damager instanceof Trident || damager instanceof Egg || damager instanceof FishHook
                || damager instanceof Snowball || !(damager instanceof Projectile)) return;

            if(damager.getShooter() instanceof Player shooter) {
                if(player == shooter) return;

                if (shooter.getHealth() - e.getFinalDamage() <= 0.0D || shooter.isBlocking())
                    return;
        
                shooter.sendMessage(ChatColor.of("#e0e0e0") + shooter.getDisplayName() + " is at " + ChatColor.WHITE
                        + (((int) (shooter.getHealth() - e.getFinalDamage())) / 2.0D) + ChatColor.of("#fa0f0f") + "❤");
            }

        }

    }
    
}
