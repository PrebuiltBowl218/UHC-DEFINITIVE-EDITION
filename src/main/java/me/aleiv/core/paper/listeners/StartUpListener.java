package me.aleiv.core.paper.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import me.aleiv.core.paper.Core;
import net.md_5.bungee.api.ChatColor;

public class StartUpListener implements Listener {

    Core instance;

    public StartUpListener(Core instance) {
        this.instance = instance;

        try {
            Scoreboard mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            //mainScoreboard.getObjectives().forEach(Objective::unregister);

            Objective obj = mainScoreboard.registerNewObjective("health_name", "health", ChatColor.DARK_RED + "❤");
            obj.setDisplaySlot(DisplaySlot.BELOW_NAME);

            Objective obj2 = mainScoreboard.registerNewObjective("health_list", "health", " ", RenderType.INTEGER);
            obj2.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            


        } catch (Exception e) {
            e.printStackTrace();
        }
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
        world.getWorldBorder().setDamageBuffer(5);
        world.getWorldBorder().setDamageAmount(0.5);
    }
}
