package me.mao.api.event;

import me.mao.core.Core;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public abstract class Event<T extends net.md_5.bungee.api.plugin.Event> implements Listener {

    public Event() {
        Core.getInstance().getProxy().getPluginManager().registerListener(Core.getInstance(), this);
    }

    @EventHandler
    public abstract void onExecute(T event);

}
