package me.mao.api.user.rank;

import java.util.LinkedList;

public interface IRank {

    String getName();

    void setName(String name);

    String getPrefix();

    void setPrefix(String prefix);

    String getSuffix();

    void setSuffix(String suffix);

    boolean isStaff();

    void setStaff(boolean staff);

    boolean isDonator();

    void setDonator(boolean donator);

    LinkedList<String> getPermissions();

    void setPermissions(LinkedList<String> permissions);

    void addPermission(String permission);

    void removePermission(String permission);

    boolean hasPermission(String permission);

    void update();

}
