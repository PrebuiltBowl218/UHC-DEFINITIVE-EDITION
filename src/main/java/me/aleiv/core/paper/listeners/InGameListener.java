package me.aleiv.core.paper.listeners;

import org.bukkit.event.Listener;

import me.aleiv.core.paper.Core;

public class InGameListener implements Listener {

    Core instance;

    public InGameListener(Core instance){
        this.instance = instance;
    }
}
