package wust.fxp.develop.platfrom.auto;

import lombok.Data;
import wust.fxp.develop.platfrom.annotation.Table;
import wust.fxp.develop.platfrom.constant.ColumnType;

/**
 * @author 凡兴鹏
 * @create 2021-04-28 11:00
 */
@Data
public class TableColumnAnnInfo {
    //字段的名称
    private String value;
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
}
