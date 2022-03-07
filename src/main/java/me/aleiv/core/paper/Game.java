package me.aleiv.core.paper;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.aleiv.core.paper.events.GameTickEvent;
import me.aleiv.core.paper.manager.EventManager;
import me.aleiv.core.paper.manager.ListenerManager;
import me.aleiv.core.paper.manager.PlayerManager;
import me.aleiv.core.paper.manager.RecipeManager;
import me.aleiv.core.paper.objects.Timer;

@Data
@EqualsAndHashCode(callSuper = false)
public class Game extends BukkitRunnable {
    Core instance;

    long gameTime = 0;
    long startTime = 0;

    Timer timer;

    //GAME DATA
    boolean pvp = false;
    GameStage gameStage = GameStage.LOBBY;
    double borderSize = 4000;

    
    //GAME CONFIG 
    boolean nether = true;
    double appleRate = 0.80;
    double flintRate = 3.00;
    int slots = 60;

    //GAME LOOP
    int borderTime = 3600;
    int borderCenterRadius = 200;
    int borderCenterTime = 1200;
    int pvpTime = 1200;

    //GAME NERFS & BUFFERS

    boolean beds = true;
    boolean bedsNerf = true;
    boolean strength = true;
    boolean strengthNerf = true;
    boolean tears = true;

    //MANAGER

    ListenerManager listenerManager;
    RecipeManager recipeManager;
    EventManager eventManager;
    PlayerManager playerManager;
    

    public Game(Core instance) {
        this.instance = instance;
        startTime = System.currentTimeMillis();

        timer = new Timer(instance, (int) gameTime);

        listenerManager = new ListenerManager(instance);
        recipeManager = new RecipeManager(instance);
        eventManager = new EventManager(instance);
        playerManager = new PlayerManager(instance);
        
    }


    @Override
    public void run() {

        var new_time = (int) (Math.floor((System.currentTimeMillis() - startTime) / 1000.0));

        gameTime = new_time;

        Bukkit.getPluginManager().callEvent(new GameTickEvent(new_time, true));
    }

    public enum GameStage {
        LOBBY, INGAME
    }

}