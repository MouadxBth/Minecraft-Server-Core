package me.mao.api.user;

import me.mao.api.user.offlineUser.IOfflineUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public interface IUser extends IOfflineUser {

    ProxiedPlayer getPlayer();

    UUID getUUID();

    String getIpAdress();

    void loadPermissions();

    void unloadPermissions();

    boolean isOnline();

}
