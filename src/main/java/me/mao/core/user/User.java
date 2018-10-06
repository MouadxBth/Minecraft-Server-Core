package me.mao.core.user;

import me.mao.api.user.IUser;
import me.mao.api.user.rank.IRank;
import me.mao.core.user.rank.Rank;
import net.md_5.bungee.api.connection.ProxiedPlayer;


import java.util.UUID;

public class User implements IUser {

    private ProxiedPlayer proxiedPlayer;
    private int level, coins;
    private double exp;
    private UUID uuid;

    public User(ProxiedPlayer proxiedPlayer, int level, int coins, double exp) {
        this.proxiedPlayer = proxiedPlayer;
        this.level = level;
        this.coins = coins;
        this.exp = exp;
        this.uuid = proxiedPlayer.getUniqueId();
    }

    public ProxiedPlayer getPlayer() {
        return proxiedPlayer;
    }


    public UUID getUUID() {
        return uuid;
    }


    public String getIpAdress() {
        return getPlayer().getAddress().getHostName();
    }


    public void loadPermissions() {
    }


    public void unloadPermissions() {

    }


    public boolean isOnline() {
        return false;
    }


    public IRank getRank() {
        return null;
    }


    public void setRank(IRank rank) {

    }


    public String getNickname() {
        return null;
    }


    public void setNickname(String nickname) {

    }


    public int getLevel() {
        return 0;
    }


    public void setLevel(int level) {

    }

    public void levelUp(int times) {

    }


    public void levelUp() {

    }


    public boolean canLevelUp() {
        return false;
    }


    public void downLevel(int times) {

    }


    public double getExperience() {
        return 0;
    }


    public void setExperience(double experience) {

    }


    public void addExperience(double experience) {

    }


    public void removeExperience(double experience) {

    }


    public int getCoins() {
        return 0;
    }


    public void setCoins(int coins) {

    }


    public void addCoins(int coins) {

    }


    public void substractCoins(int coins) {

    }

}
