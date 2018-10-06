package me.mao.system.exceptions;

import me.mao.core.utils.Messenger;
import net.md_5.bungee.api.plugin.Plugin;


public class RedisNotConnectedException extends Exception {

    public RedisNotConnectedException(Plugin plugin){
        super("");
        Messenger.error("&cCould not connect to redis, please check the informations you've included! shuttingdown...");
        plugin.getProxy().stop();
    }

}
