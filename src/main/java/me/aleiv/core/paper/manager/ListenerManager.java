package me.aleiv.core.paper.manager;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import me.aleiv.core.paper.Core;
import me.aleiv.core.paper.listeners.ConfigListener;
import me.aleiv.core.paper.listeners.DefaultListener;
import me.aleiv.core.paper.listeners.GlobalListener;
import me.aleiv.core.paper.listeners.GracePeriodListener;
import me.aleiv.core.paper.listeners.InGameListener;
import me.aleiv.core.paper.listeners.LobbyListener;
import me.aleiv.core.paper.listeners.StartUpListener;

public class ListenerManager {
    
    Core instance;

    GlobalListener globalListener;
    GracePeriodListener gracePeriodListener;
    InGameListener inGameListener;
    LobbyListener lobbyListener;
    StartUpListener startUpListener;
    ConfigListener configListener;
    DefaultListener defaultListener;
    
    public ListenerManager(Core instance){
        this.instance = instance;

        globalListener = new GlobalListener(instance);
        gracePeriodListener = new GracePeriodListener(instance);
        inGameListener = new InGameListener(instance);
        lobbyListener = new LobbyListener(instance);
        startUpListener = new StartUpListener(instance);
        configListener = new ConfigListener(instance);
        defaultListener = new DefaultListener(instance);

        //STATIC

        registerListener(globalListener);
        registerListener(inGameListener);
        registerListener(startUpListener);
        registerListener(configListener);
        registerListener(defaultListener);

        //DINAMIC

        registerListener(gracePeriodListener);
        registerListener(lobbyListener);

    }

    public void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, instance);
    }
}
