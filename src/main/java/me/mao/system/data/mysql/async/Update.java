package me.mao.system.data.mysql.async;


import me.mao.system.data.Callback;
import me.mao.system.data.mysql.MySQLAcces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update implements Runnable {

    private PreparedStatement preparedStatement;
    private MySQLAcces acces;
    private Callback<Integer, SQLException> callback;
    private Connection connection = acces.getConnection();

    public Update(PreparedStatement statement, MySQLAcces acces, Callback<Integer, SQLException> callback) {
        this.preparedStatement = statement;
        this.acces = acces;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            if (callback != null) {
                callback.call(preparedStatement.executeUpdate(), null);
            }
        } catch (SQLException e) {
            if (callback != null) {
                callback.call(null, e);
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
