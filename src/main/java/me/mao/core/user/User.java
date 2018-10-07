package me.mao.core.user;

import me.mao.core.user.rank.Rank;
import net.md_5.bungee.api.connection.ProxiedPlayer;


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class User {

    private ProxiedPlayer proxiedPlayer;
    private UUID uuid;
    private int level;
    private int coins;
    private double exp;
    private Rank rank;
    private Rank subrank;
    private LinkedList<String> permissions;


    public User(ProxiedPlayer proxiedPlayer, int level, int coins, double exp, Rank rank, Rank subrank, LinkedList<String> permissions) {
        this.proxiedPlayer = proxiedPlayer;
        this.level = level;
        this.coins = coins;
        this.exp = exp;
        this.rank = rank;
        this.subrank = subrank;
        this.permissions = permissions;
    }

    public User(UUID uuid, int level, int coins, double exp, Rank rank, Rank subrank, LinkedList<String> permissions) {
        this.uuid = uuid;
        this.level = level;
        this.coins = coins;
        this.exp = exp;
        this.rank = rank;
        this.subrank = subrank;
        this.permissions = permissions;
    }

    public String getIpAdress() {
        return getProxiedPlayer().getAddress().getHostName();
    }

    public ProxiedPlayer getProxiedPlayer() {
        return proxiedPlayer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel(int level) {
        this.level += level;
    }

    public void substractLevel(int level) {
        this.level -= level;
    }

    public void upgradeLevel() {
        if (this.exp == this.level + 1000) {
            addLevel(1);
            resetExp();
        }
    }

    public boolean canUpgrade() {
        return this.exp == this.level + 1000;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void addExp(double exp) {
        this.exp += exp;
    }

    public void substractExp(double exp) {
        this.exp -= exp;
    }

    public void resetExp() {
        setExp(0.0);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void substractCoins(int coins) {
        this.coins -= coins;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public boolean hasRank() {
        return rank != null;
    }

    public Rank getSubrank() {
        return subrank;
    }

    public void setSubrank(Rank subrank) {
        this.subrank = subrank;
    }

    public boolean hasSubrank() {
        return subrank != null;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(LinkedList<String> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public void addPermission(String permission) {
        if(hasPermission(permission)) return;
        permissions.add(permission);
    }

    public void removePermission(String permission) {
        if(!hasPermission(permission)) return;
        permissions.remove(permission);
    }

    public void loadPermissions() {
        rank.getPermissions().forEach(perm -> {
            if(hasPermission(perm)) return;
            addPermission(perm);
        });

        subrank.getPermissions().forEach(perm -> {
            if(hasPermission(perm)) return;
            addPermission(perm);
        });

        getPermissions().forEach(perm -> {
            getProxiedPlayer().setPermission(perm, true);
        });
    }

    public void unloadPermissions() {
        getProxiedPlayer().getPermissions().forEach(perm -> getProxiedPlayer().setPermission(perm, false));
    }
}
