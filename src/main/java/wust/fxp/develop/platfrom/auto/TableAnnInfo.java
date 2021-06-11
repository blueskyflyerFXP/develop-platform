package wust.fxp.develop.platfrom.auto;

import lombok.Data;
import wust.fxp.develop.platfrom.annotation.Table;
import wust.fxp.develop.platfrom.constant.DbType;
import wust.fxp.develop.platfrom.constant.StyleType;

/**
 * @author 凡兴鹏
 * @create 2021-04-28 10:59
 */
@Data
public class TableAnnInfo {
    //表名
   private String value;
    //表名
    private String tableName;
    //表注释
    private String tableDesc;
    //数据库类型
    private DbType dbType;

    private StyleType modelFormat;
}
