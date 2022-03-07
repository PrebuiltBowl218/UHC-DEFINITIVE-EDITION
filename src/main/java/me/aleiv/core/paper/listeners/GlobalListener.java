package me.aleiv.core.paper.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.aleiv.core.paper.Core;
import me.aleiv.core.paper.Game.GameStage;
import me.aleiv.core.paper.events.GameTickEvent;
import me.aleiv.core.paper.events.NetherChangeEvent;

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

    public void joinMessage(PlayerJoinEvent e) {
        e.setJoinMessage("");

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
    }

    
    //NETHER DISABLED
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
