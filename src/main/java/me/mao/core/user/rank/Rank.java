package me.mao.core.user.rank;

import me.mao.api.user.rank.IRank;

import java.util.LinkedList;
import java.util.List;

public class Rank implements IRank {


    private String name, prefix, suffix;
    private boolean staff, donator;
    private LinkedList<String> permissions;

    public Rank(String name, String prefix, String suffix, boolean staff, boolean donator, LinkedList<String> permissions) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.staff = staff;
        this.donator = donator;
        this.permissions = permissions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        update();
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
        update();
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
        update();
    }

    @Override
    public boolean isStaff() {
        return staff;
    }

    @Override
    public void setStaff(boolean staff) {
        this.staff = staff;
        update();
    }

    @Override
    public boolean isDonator() {
        return donator;
    }

    @Override
    public void setDonator(boolean donator) {
        this.donator = donator;
        update();
    }

    @Override
    public LinkedList<String> getPermissions() {
        return permissions;
    }

    @Override
    public void setPermissions(LinkedList<String> permissions) {
        this.permissions = permissions;
        update();
    }

    @Override
    public void addPermission(String permission) {
        if(getPermissions().contains(permission) ? null : getPermissions().add(permission));
        update();
    }

    @Override
    public void removePermission(String permission) {
        if(!getPermissions().contains(permission) ? null : getPermissions().remove(permission));
    }

    @Override
    public boolean hasPermission(String permission) {
        return getPermissions().contains(permission);
    }

    public void update() {

    }
}
