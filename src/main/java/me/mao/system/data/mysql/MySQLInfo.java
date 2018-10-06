package me.mao.system.data.mysql;

import me.mao.core.Core;
import me.mao.core.utils.Config;

public class MySQLInfo {

    private Config settings;

    public MySQLInfo() {
        this.settings = new Config("settings");
    }

    protected MySQLCredentials getMySQLCredentiels() {
        if (settings.getKeys().isEmpty()) {
            settings.set("mysql.username", "");
            settings.set("mysql.password", "");
            settings.set("mysql.database", "");
            settings.set("mysql.hostname", "");
            settings.set("mysql.port", "");
            return null;
        }

        String username = settings.getString("mysql.username");
        String password = settings.getString("mysql.password");
        String database = settings.getString("mysql.database");
        String host = settings.getString("mysql.hostname");
        int port = settings.getInteger("mysql.port");

        return new MySQLCredentials(host, port, username, password, database);
    }

}
