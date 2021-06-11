package wust.fxp.develop.platfrom.constant;

/**
 * @author 凡兴鹏
 * @create 2021-04-27 22:22
 */
public enum  DbType {
    ORACLE("oracle","oracle.jdbc.driver.OracleDriver"),
    MY_SQL("mysql","com.mysql.cj.jdbc.Driver");

    DbType(String dbName, String dbDriver) {
        this.dbName = dbName;
        this.dbDriver = dbDriver;
    }

    DbType(String dbName, String dbDriver, String dbVersion, String dbDesc) {
        this.dbName = dbName;
        this.dbDriver = dbDriver;
        this.dbVersion = dbVersion;
        this.dbDesc = dbDesc;
    }

    private String dbName;

    private String dbDriver;

    private String dbVersion;

    private String dbDesc;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getDbDesc() {
        return dbDesc;
    }

    public void setDbDesc(String dbDesc) {
        this.dbDesc = dbDesc;
    }
}
