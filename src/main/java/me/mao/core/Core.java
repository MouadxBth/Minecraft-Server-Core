package me.mao.core;

import me.mao.core.utils.Messenger;
import net.md_5.bungee.api.plugin.Plugin;


public class Core extends Plugin {

    private static Core instance;

    public void onEnable() {
        long time = System.currentTimeMillis();
        loadDepends();
        setup();
        loadCommands();
        loadListeners();
        Messenger.info("&6Finished enabling in " + (System.currentTimeMillis() - time));
    }

    public void onDisable() {
        shutdown();
        unloadDepends();
    }

    private void loadDepends() {
        instance = this;
    }

    private void unloadDepends() {
        instance = null;
    }

    private void setup() {
    }

    private void shutdown() {
    }

    private void loadCommands() {
    }

    private void loadListeners() {
    }

    public static Core getInstance() {
        return instance;
    }

}
