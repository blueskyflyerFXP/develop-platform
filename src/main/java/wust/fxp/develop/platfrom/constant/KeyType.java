package wust.fxp.develop.platfrom.constant;

import java.util.Random;
import java.util.UUID;

/**
 * 主键的类型
 * @author 凡兴鹏
 * @create 2021-04-27 22:51
 */
public enum KeyType {

    PRIMARY_KEY("primary_key","唯一主键","primary key",1),
    NOT_NULL_KEY("not_null_key","唯一主键","not null",1),
    //不推荐使用外键
    FREGIN_KEY("foreign_key","外键","foreign key",2),
    UNIQUE_KEY("unique_key","唯一性键","unique",1),
    UNION_KEY("union_key","联合主键","primary key",2);

    public static long genNum=0;


    KeyType(String keyName, String keyDesc, String dbDesc) {
        this.keyName = keyName;
        this.keyDesc = keyDesc;
        this.dbDesc = dbDesc;
    }

    KeyType(String keyName, String keyDesc, String dbDesc, int argNum) {
        this.keyName = keyName;
        this.keyDesc = keyDesc;
        this.dbDesc = dbDesc;
        this.argNum = argNum;
    }

    //键的名称
    private String keyName;
    //键的描述
    private String keyDesc;
    //数据库描述
    private String dbDesc;
    //至少需要的参数
    private int argNum;

    public String GrenerKey(int nowCount){
        switch (this.getKeyName()){
            case "by_uuid":
                return   UUID.randomUUID().toString();
            case "by_time":
                return String.valueOf(System.currentTimeMillis());
            case "by_count":
                return String.valueOf(++nowCount);
            case "by_random":
                return String.valueOf(new Random().longs(genNum));
            default:
                return   UUID.randomUUID().toString();
        }
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyDesc() {
        return keyDesc;
    }

    public void setKeyDesc(String keyDesc) {
        this.keyDesc = keyDesc;
    }

    public static long getGenNum() {
        return genNum;
    }

    public static void setGenNum(long genNum) {
        KeyType.genNum = genNum;
    }

    public String getDbDesc() {
        return dbDesc;
    }

    public void setDbDesc(String dbDesc) {
        this.dbDesc = dbDesc;
    }
}
