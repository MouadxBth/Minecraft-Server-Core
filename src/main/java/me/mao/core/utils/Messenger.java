package me.mao.core.utils;

import me.mao.core.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class Messenger {

    private static Core core = Core.getInstance();

    public static void alert(String message) {
        core.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&7[&6Alert&7]: &r" + message)));
    }

    public static void info(String message) {
        core.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&7[&Info&7]: &r" + message)));
    }

    public static void warn(String message) {
        core.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&7[&5Warning&7]: &r" + message)));
    }

    public static void error(String message) {
        core.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&7[&cError&7]: &r" + message)));
    }

    public static TextComponent format(String message) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void broadcast(String message) {
        List<ProxiedPlayer> playerList = new ArrayList<>();

        for(ServerInfo server : core.getProxy().getServers().values()){
            for(ProxiedPlayer player : server.getPlayers()) {
                playerList.add(player);
            }
        }

        playerList.forEach(player -> player.sendMessage(format(message)));

    }

}
