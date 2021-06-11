package wust.fxp.develop.platfrom.constant;


import wust.fxp.develop.platfrom.utils.DateUtils;

/**
 * @author 凡兴鹏
 * @create 2021-04-27 22:23
 */
public enum ColumnType {
    BOOL("bool","number","存储布尔值",1),
    BYTE("byte","number","存储微小整数",2),
    SHORT("short","number","存储短整数",6),
    INT("int","number","存储正常整数",16),
    LONG("long","number","存储长整数",32),
    FOLAT("folat","binary_float","存储浮点数"),
    DOUBLE("double","binary_double","存储高精度浮点数"),
    NUMBER("number","number","存储确定精度的浮点值",10,2),
    MIN_STR("min_str","varchar2","存储最小的字符串",4),
    SMALL_STR("small_str","varchar2","存储短字符串",12),
    LITTER_STR("litter_str","varchar2","存储稍微短字符串",20),
    NORMAL_STR("normal_str","varchar2","存储正常长度的字符串",45),
    LITTER_LONG_STR("litter_long_str","varchar2","存储比较长的字符串",350),
    LONG_STR("long_str","varchar2","存储长字符串",2000),
    MAX_STR("max_str","varchar2","存储超长的字符串",3800),
    TEXT("text","nclob","存储大文本"),
    BYTE_DATA("byte_data","blob","存储二进制数据"),
    DATE("date","date","存储日期(年月日)", DateUtils.YMD),
    TIME("time","date","存储日期(时分秒)",DateUtils.HMS),
    DATATIME("datetime","date","存储日期(年月日和时分秒)",DateUtils.YMDHMS),
    DATETIMES("datatimes","date","存储日期(年月日和时分秒以及毫秒)",DateUtils.YMDHMSS),
    TIMSTAMP("timestamp","timestamp","存储时间戳");


    ColumnType(String typeName,String dbDefaultType, String typeDesc) {
        this.typeName = typeName;
        this.typeDesc = typeDesc;
        this.dbDefaultType=dbDefaultType;
    }

    ColumnType(String typeName,String dbDefaultType, String typeDesc, String formatRule) {
        this.typeName = typeName;
        this.typeDesc = typeDesc;
        this.formatRule = formatRule;
        this.dbDefaultType=dbDefaultType;
    }

    ColumnType(String typeName,String dbDefaultType, String typeDesc, int arg0) {
        this.typeName = typeName;
        this.typeDesc = typeDesc;
        this.dbDefaultType=dbDefaultType;
        this.arg0 = arg0;
    }

    ColumnType(String typeName,String dbDefaultType, String typeDesc, int arg0, int arg1) {
        this.typeName = typeName;
        this.typeDesc = typeDesc;
        this.dbDefaultType=dbDefaultType;
        this.arg0 = arg0;
        this.arg1 = arg1;
    }

    //类型名称
    private String typeName;
    //类型的描述
    private String typeDesc;
    //类型的默认类型，数据库默认为Oracle
    private String dbDefaultType;
    //第一个参数
    private int arg0;
    //第二个参数
    private int arg1;
    //风格
    private String formatRule;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getDbDefaultType() {
        return dbDefaultType;
    }

    public void setDbDefaultType(String dbDefaultType) {
        this.dbDefaultType = dbDefaultType;
    }

    public int getArg0() {
        return arg0;
    }

    public void setArg0(int arg0) {
        this.arg0 = arg0;
    }

    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public String getFormatRule() {
        return formatRule;
    }

    public void setFormatRule(String formatRule) {
        this.formatRule = formatRule;
    }
}
