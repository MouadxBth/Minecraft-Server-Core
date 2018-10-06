package me.mao.api.user.offlineUser;

import me.mao.api.user.rank.IRank;

public interface IOfflineUser {

    IRank getRank();

    void setRank(IRank rank);

    String getNickname();

    void setNickname(String nickname);

    int getLevel();

    void setLevel(int level);

    void levelUp(int times);

    void levelUp();

    boolean canLevelUp();

    void downLevel(int times);

    double getExperience();

    void setExperience(double experience);

    void addExperience(double experience);

    void removeExperience(double experience);

    int getCoins();

    void setCoins(int coins);

    void addCoins(int coins);

    void substractCoins(int coins);


}
