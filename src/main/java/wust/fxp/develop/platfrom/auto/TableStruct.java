package wust.fxp.develop.platfrom.auto;

import lombok.Data;
import wust.fxp.develop.platfrom.constant.ColumnType;
import wust.fxp.develop.platfrom.constant.KeyGenerator;
import wust.fxp.develop.platfrom.constant.KeyType;

/**
 * 表信息
 * @author 凡兴鹏
 * @create 2021-04-29 15:17
 */
@Data
public class TableStruct {
    //表的名称
    private String tableName;
    //表的别名
    private String fieldAlias="t";
    //属性的名称
    private String fieldName;
    //字段的名称
    private String columName;
    //字段的默认值
    private String defaultValue;
    //字段的类型
    private ColumnType columnType;
    //数据库的字段类型
    private String dbTypeName;
    //字段注释
    private String columComment;
    //键类型
    private KeyType keyType;
    //键生成器
    private KeyGenerator keyGrener;
    //仅对随机生成器有效，表示随机数的最大值(正整数)
    private int genNum;
    //表示键的组别，当是联合主键时有效，表示哪几个联合主键联合唯一
    private int groupNum;
    //是否是索引
    private boolean index;
}
