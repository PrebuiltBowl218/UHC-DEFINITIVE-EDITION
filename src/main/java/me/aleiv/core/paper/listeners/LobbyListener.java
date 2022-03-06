package me.aleiv.core.paper.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.aleiv.core.paper.Core;
import me.aleiv.core.paper.Game.GameStage;

public class LobbyListener implements Listener {

    Core instance;

    public LobbyListener(Core instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();
        var game = instance.getGame();
        var stage = game.getGameStage();

        if (stage == GameStage.LOBBY) {
            var world = Bukkit.getWorlds().get(0);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(world.getSpawnLocation());
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        var game = instance.getGame();
        var stage = game.getGameStage();

        if (stage == GameStage.LOBBY)
            e.setCancelled(true);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        var game = instance.getGame();
        var stage = game.getGameStage();

        if (stage == GameStage.LOBBY && !e.getPlayer().hasPermission("lobby.edit"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        var game = instance.getGame();
        var stage = game.getGameStage();

        if (stage == GameStage.LOBBY && !e.getPlayer().hasPermission("lobby.edit"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        var game = instance.getGame();
        var stage = game.getGameStage();

        if (stage == GameStage.LOBBY) {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

}
