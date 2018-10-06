package me.mao.api.event;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public abstract class Event<T extends net.md_5.bungee.api.plugin.Event> implements Listener {

    @EventHandler
    public abstract void onExecute(T event);

}
