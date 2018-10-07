package me.mao.core;

import me.mao.core.events.PlayerDisconnect;
import me.mao.core.events.PlayerPostLogin;
import me.mao.core.events.PlayerPreLogin;
import me.mao.core.utils.Messenger;
import me.mao.system.communicator.Communicator;
import net.md_5.bungee.api.plugin.Plugin;


public class Core extends Plugin {

    private static Core instance;
    private Communicator communicator;

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
        communicator = new Communicator(this);
    }

    private void unloadDepends() {
        instance = null;
    }

    private void setup() {
        communicator.startConnection();
    }

    private void shutdown() {
        communicator.startConnection();
    }

    private void loadCommands() {
    }

    private void loadListeners() {
        new PlayerPreLogin();
        new PlayerPostLogin();
        new PlayerDisconnect();
    }

    public static Core getInstance() {
        return instance;
    }

    public Communicator getCommunicator() {
        return communicator;
    }
}
