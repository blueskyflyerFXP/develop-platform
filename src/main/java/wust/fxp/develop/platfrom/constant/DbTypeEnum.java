package wust.fxp.develop.platfrom.constant;

/**
 * @author 凡兴鹏
 * @create 2021-04-24 19:39
 */
public enum DbTypeEnum {
    ORACLE("oracle","","oracle 11g"),
    MY_SQL("mysql","com.mysql.cj.jdbc.Driver","mysql");

    DbTypeEnum(String dbType, String dbDirver, String dbDesc) {
        this.dbType = dbType;
        this.dbDirver = dbDirver;
        this.dbDesc = dbDesc;
    }

    /**数据库类型*/
    private String dbType;
    /**数据库驱动名称*/
    private String dbDirver;
    /**数据库描述*/
    private String dbDesc;

}
