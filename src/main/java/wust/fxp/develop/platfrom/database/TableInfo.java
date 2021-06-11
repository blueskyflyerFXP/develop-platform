package wust.fxp.develop.platfrom.database;

import lombok.Data;
import org.checkerframework.checker.units.qual.K;
import wust.fxp.develop.platfrom.annotation.Key;
import wust.fxp.develop.platfrom.annotation.Table;

import java.util.List;

/**
 * 表数据的结构
 * @author 凡兴鹏
 * @create 2021-05-06 22:33
 */
@Table
@Data
public class TableInfo {
    //表ID
    @Key
    private String tableId;
    //所在数据库的名称或者模式名称
    private String databaseName;
    //数据表名
    private String tableName;
    //表别名
    private String tableAliasName;
    //表注释
    private String tableComment;
    //表类型：普通，字典
    private String tableType;
    //表的字符集
    private String tableCharset;
    //表的引擎
    private String tableEngine;
    //表的创建时间
    private String createTime;
    //表的创建者
    private String createUser;
    //表的最近更新人
    private String modifyTime;
    //表的最新更新时间
    private String modifyUser;
    //表的字段
    private List<TableColumn> tableColumnList;
}
