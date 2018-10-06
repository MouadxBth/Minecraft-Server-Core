package me.mao.system.data.mysql;

public class MySQLConnection {

    private MySQLAcces mySQLAcces;

    public MySQLConnection() {
    }

    public boolean startConnection() {
        if(mySQLAcces == null) {
            this.mySQLAcces = new MySQLAcces(new MySQLInfo().getMySQLCredentiels());
            if(!mySQLAcces.isConnected()) {
                mySQLAcces.init();
                return true;
            }
        }

        return false;
    }

    public boolean stopConnection() {
        if(mySQLAcces == null) return false;
        mySQLAcces.shutdown();
        return true;
    }

    public boolean isConnected() {
        if(mySQLAcces == null) return false;
        return mySQLAcces.connected;
    }

    public MySQLAcces getMySQLAcces() {
        return mySQLAcces;
    }
}
