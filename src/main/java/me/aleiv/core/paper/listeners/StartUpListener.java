package me.aleiv.core.paper.listeners;

import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import me.aleiv.core.paper.Core;

public class StartUpListener implements Listener {

    Core instance;

    public StartUpListener(Core instance) {
        this.instance = instance;
    }

    @EventHandler
    public void worldLoad(WorldLoadEvent e) {
        var world = e.getWorld();
        var game = instance.getGame();
        world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        world.setSpawnLocation(0, world.getHighestBlockAt(0, 0).getZ() + 10, 0);
        world.getWorldBorder().setCenter(0, 0);
        world.getWorldBorder().setSize(game.getBorderSize());
        world.getWorldBorder().setDamageBuffer(0.0);
        world.getWorldBorder().setDamageAmount(0.0);
    }
}
