package wust.fxp.develop.platfrom.auto;

import lombok.Data;
import wust.fxp.develop.platfrom.constant.KeyGenerator;
import wust.fxp.develop.platfrom.constant.KeyType;

/**
 * @author 凡兴鹏
 * @create 2021-04-28 10:57
 */
@Data
public class TableKeyAnnInfo {
    //键类型
    private KeyType keyType;
    //键生成器
    private KeyGenerator keyGrener;
    //仅对随机生成器有效，表示随机数的最大值(正整数)
    private int genNum;
    //表示键的组别，当是联合主键时有效，表示哪几个联合主键联合唯一
    private int groupNum;
}
