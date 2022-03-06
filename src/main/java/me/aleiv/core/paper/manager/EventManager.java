package me.aleiv.core.paper.manager;

import java.util.HashMap;
import java.util.function.Consumer;

import lombok.Getter;
import me.aleiv.core.paper.Core;

public class EventManager {
    
    Core instance;

    @Getter HashMap<Integer, Consumer<Integer>> events = new HashMap<>();

    public EventManager(Core instance){
        this.instance = instance;

        //PVP
        events.put(600, c ->{
            
        });
    }

    

    

}
