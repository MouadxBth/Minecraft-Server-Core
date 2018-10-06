package me.mao.system.exceptions;

import me.mao.core.utils.Messenger;
import net.md_5.bungee.api.plugin.Plugin;

public class MySqlNotConnectedException extends Exception {

    public MySqlNotConnectedException(Plugin plugin) {
        super("");
        Messenger.error("&cCould not connect to mysql database! please check the informations you've included! shuttingdown...");
        plugin.getProxy().stop();
    }

}
