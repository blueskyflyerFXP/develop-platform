package wust.fxp.develop.platfrom.auto;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import wust.fxp.develop.platfrom.annotation.Column;
import wust.fxp.develop.platfrom.annotation.Index;
import wust.fxp.develop.platfrom.annotation.Key;
import wust.fxp.develop.platfrom.annotation.Table;
import wust.fxp.develop.platfrom.constant.ColumnType;
import wust.fxp.develop.platfrom.constant.DbType;
import wust.fxp.develop.platfrom.constant.KeyType;
import wust.fxp.develop.platfrom.constant.StyleType;
import wust.fxp.develop.platfrom.utils.CollectionUtils;
import wust.fxp.develop.platfrom.utils.ObjectUtil;
import wust.fxp.develop.platfrom.utils.StringUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 自动生成表结构、基本查询语句、插入语句、更新语句和删除语句
 *
 * @author 凡兴鹏
 * @create 2021-04-28 18:21
 */
@Slf4j
public class AutoGen {
    private static String defaultId = "id";
    private static String alias="t";

    private AutoScann autoScann;

    //初始化生成器
    private static AutoGen initGen(String baseScannDir, String basePacket) {
        AutoGen autoGen = new AutoGen();
        try {
            autoGen.autoScann = AutoScann.autoScann("\\src\\main\\java\\", basePacket);
        } catch (Exception e) {
            log.info("初始化生成器错误,扫描器出现错误", e);
            return autoGen;
        }
        return autoGen;
    }

    public static AutoGen genStart(String baseScannDir, String scannPacket, String saveDir) throws Exception {
        String dirPath = System.getProperty("user.dir");
        //扫描Table类注解、Key属性注解、Column注解和Index注解，构建表结构，插入语句，查询语句，删除语句，修改语句
        AutoGen autoGen = AutoGen.initGen(baseScannDir, scannPacket);
        //代码风格检查
        autoGen.autoScann.checkClassStyle(StyleType.BIG_HUMP);
        autoGen.autoScann.checkFeildStyle(StyleType.LITTLE_HUMP);

        //扫描类注解
        autoGen.autoScann.scannClassAnn(Table.class, TableAnnInfo.class);
        //扫描属性注解
        autoGen.autoScann.scannFieldAnn(Key.class, TableKeyAnnInfo.class);
        autoGen.autoScann.scannFieldAnn(Index.class, TableIndexAnnInfo.class);
        autoGen.autoScann.scannFieldAnn(Column.class, TableColumnAnnInfo.class);
        //获取表信息
        Map<TableAnnInfo, List<TableStruct>> tableInfoListMap = autoGen.getTableStruct();
        if (CollectionUtils.isEmpty(tableInfoListMap)) {
            return autoGen;
        }
        //生成SQL语句
        autoGen.genSQL(tableInfoListMap, dirPath, "UTF-8");
        return autoGen;
    }

    private void genSQL(Map<TableAnnInfo, List<TableStruct>> tableInfoListMap, String baseDir, String charsetName) {
        //表结构
        StringBuffer createTableStr = new StringBuffer("");
        //表约束
        StringBuffer keyStr = new StringBuffer("");
        //列注释
        StringBuffer commentStr = new StringBuffer("");

        String dbName = "oracle";
        for (TableAnnInfo tableAnnInfo : tableInfoListMap.keySet()) {
            createTableStr.append("create table ").append(tableAnnInfo.getTableName());;
            //添加表注释
            if (!StringUtils.isEmpty(tableAnnInfo.getTableDesc())) {
                createTableComment(commentStr, tableAnnInfo);
            }
            createTableStr.append(" (");
            //创建表字段，如果没有字段或者没有主键的情况：会自动创建id字段作为主键,如果有id字段自动设置为主键
            boolean hasKey = false;
            if (!ObjectUtil.isEmpty(tableInfoListMap.get(tableAnnInfo))) {
                //创建表结构、约束和字段注释,增删改查语句
                hasKey = createTable(tableAnnInfo, tableInfoListMap.get(tableAnnInfo), createTableStr,commentStr,keyStr,hasKey,baseDir);
            }
            //如果没有主键也没有id字段，创建id字段为主键
            if (!hasKey) {
                TableStruct idStruct = new TableStruct();
                idStruct.setColumName(defaultId);
                idStruct.setTableName(tableAnnInfo.getTableName());
                idStruct.setColumnType(ColumnType.NORMAL_STR);
                idStruct.setColumComment("唯一ID");
                createTableColumn(createTableStr, commentStr, idStruct);
                createColumnComment(commentStr, idStruct);
            }
            createTableStr = new StringBuffer(createTableStr.substring(0, createTableStr.length() - 1));
            createTableStr.append("\n);\n");
            createTableStr.append(commentStr).append("\n").append(keyStr);
            saveFile(baseDir + "/sql/" + dbName + "/table/"+ tableAnnInfo.getTableName()+".sql", createTableStr.toString(), charsetName);
            createTableStr=new StringBuffer("");
            commentStr=new StringBuffer("");
            keyStr=new StringBuffer("");
        }
    }

    private boolean createTable(TableAnnInfo tableAnnInfo, List<TableStruct> tableStructList, StringBuffer createTableStr, StringBuffer commentStr ,
                                StringBuffer keyStr, boolean hasKey, String baseDir ) {

        //查询语句
        StringBuffer selectStr=new StringBuffer("");
        //增加数据的语句
        StringBuffer insertStr=new StringBuffer("");
        //修改语句
        StringBuffer updateStr=new StringBuffer("");
        //删除语句
        StringBuffer deleteStr=new StringBuffer("");
        StringBuffer insert1Str=new StringBuffer("");
        StringBuffer select1Str=new StringBuffer("");
        selectStr.append("select ");
        insertStr.append("insert into ").append(tableAnnInfo.getTableName());
        updateStr.append("update ");
        deleteStr.append("delete from ").append(tableAnnInfo.getTableName()).append(" ").append(alias);
        //创建表及其注释
        updateStr.append(tableAnnInfo.getTableName()).append(" set ");
        deleteStr.append(" where ");
        insertStr.append("(");
        for (TableStruct tableStruct : tableStructList) {

            if (!ObjectUtil.isEmpty(tableStruct)) {
                if (KeyType.PRIMARY_KEY.equals(tableStruct.getKeyType()) || KeyType.FREGIN_KEY.equals(tableStruct.getKeyType())) {
                    if(!StringUtils.isEmpty(tableStruct.getColumName())) {
                        deleteStr.append(alias).append(".").append(tableStruct.getColumName()).append(" = #{").append(tableStruct.getFieldName()).append("} ");
                    }
                    hasKey = true;
                }
                //构建列结构
                if (!StringUtils.isEmpty(tableStruct.getColumName())) {
                    createTableColumn(createTableStr, commentStr, tableStruct);
                    insertStr.append(tableStruct.getColumName()).append(",");
                    insert1Str.append("#{").append(tableStruct.getFieldName()).append("},");
                    selectStr.append(alias).append(".").append(tableStruct.getColumName()).append(" ").append(tableStruct.getFieldName()).append( " ,");
                    select1Str.append(" ").append(alias).append(".").append(tableStruct.getColumName()).append(" = #{").append(tableStruct.getFieldName()).append("} and");
                    updateStr.append(alias).append(".").append(tableStruct.getColumName()).append(" = #{").append(tableStruct.getFieldName()).append("},");
                }
                //添加列约束
                if (!ObjectUtil.isEmpty(tableStruct.getKeyType())) {
                    createTableKey(keyStr, tableStruct);
                }
                //添加列注释
                if (!StringUtils.isEmpty(tableStruct.getColumComment())) {
                    createColumnComment(commentStr, tableStruct);
                }
            }
        }


        selectStr=new StringBuffer(selectStr.substring(0,selectStr.length()-1));
        select1Str=new StringBuffer(select1Str.substring(0,select1Str.length()-3));
        insert1Str=new StringBuffer(insert1Str.substring(0,insert1Str.length()-1));
        insertStr=new StringBuffer(insertStr.substring(0,insertStr.length()-1));
        updateStr=new StringBuffer(updateStr.substring(0,updateStr.length()-1));
        selectStr.append("from ").append(tableAnnInfo.getTableName()).append(" ").append(alias)
                .append(" where ").append(select1Str).append("\n").append(insertStr)
                .append(")").append(" values(").append(insert1Str).append(")\n").append(updateStr).append("\n").append(deleteStr);

        saveFile(baseDir + "/sql/oracle/crud/"+ tableAnnInfo.getTableName()+".sql", selectStr.toString(), "UTF-8");
        return hasKey;
    }

    private void createTableKey(StringBuffer keyStr, TableStruct tableStruct) {
        KeyType keyType = tableStruct.getKeyType();
        keyStr.append("alter table ").append(tableStruct.getTableName()).append(" add constraint ")
                .append(tableStruct.getTableName()).append("_").append(keyType.getKeyName())
                .append(" ").append(keyType.getDbDesc()).append("(").append(tableStruct.getColumName()).append(");\n");
    }

    //创建表字段
    private void createTableColumn(StringBuffer createTableStr, StringBuffer commentStr, TableStruct tableStruct) {
        createTableStr
                .append("\n\t")
                .append(tableStruct.getColumName()).append(" ")
                .append(tableStruct.getColumnType().getDbDefaultType());
        if (tableStruct.getColumnType().getArg0() > 0) {
            createTableStr.append("(").append(tableStruct.getColumnType().getArg0());
            if (tableStruct.getColumnType().getArg1() > 0) {
                createTableStr.append(",").append(tableStruct.getColumnType().getArg1());
            }
            createTableStr.append(")");
        }
        createTableStr.append(",");
    }

    private void createTableComment(StringBuffer commentStr, TableAnnInfo tableAnnInfo) {
        createComment(commentStr, "table", tableAnnInfo.getTableName(), "", tableAnnInfo.getTableDesc());
    }

    private void createColumnComment(StringBuffer commentStr, TableStruct tableStruct) {
        createComment(commentStr, "column", tableStruct.getTableName(), tableStruct.getColumName(), tableStruct.getColumComment());
    }

    //创建注释
    private void createComment(StringBuffer commentStr, String typeDesc, String tableName, String columnName, String commentContext) {
        commentStr.append("\ncomment on ").append(typeDesc).append(" ").append(tableName);
        if (!StringUtils.isEmpty(columnName)) {
            //创建列注释
            commentStr.append(".").append(columnName);
        }
        commentStr.append(" is '").append(commentContext).append("';");
    }

    //获取表信息
    public Map<TableAnnInfo, List<TableStruct>> getTableStruct() {
        StringBuffer sb = new StringBuffer("");
        String tableName = "";
        Map<TableAnnInfo, List<TableStruct>> tableInfoMap = new HashMap<>();
        //存储表的列信息
        List<TableStruct> tableStructList = null;
        Map<Class, Object> tableMap = autoScann.getClassAnnQueue().get(Table.class);

        //获取Colum注解修饰的类属性
        Map<Class, Map<Field, Object>> columMap = autoScann.getFieldAnnQueue().get(Column.class);
        if (CollectionUtils.isEmpty(columMap)) {
            //如果为空，使用默认的类属性(小驼峰式）变为下划线式
            columMap = new HashMap<>();
        }
        //获取Key注解修饰的类属性
        Map<Class, Map<Field, Object>> keyMap = autoScann.getFieldAnnQueue().get(Key.class);
        if (CollectionUtils.isEmpty(keyMap)) {
            //如果没有唯一键字段（包括此处的没有Key修饰的字段），自动创建id字段作为唯一主键或者把类属性的id字段作为唯一主键
            keyMap = new HashMap<>();
        }
        //获取Index注解修饰的类属性,没有Index修饰的属性，不创建索引
        Map<Class, Map<Field, Object>> indexMap = autoScann.getFieldAnnQueue().get(Index.class);
        if (CollectionUtils.isEmpty(indexMap)) {
            //如果没有唯一键字段（包括此处的没有Key修饰的字段），自动创建id字段作为唯一主键或者把类属性的id字段作为唯一主键
            indexMap = new HashMap<>();
        }

        if (!CollectionUtils.isEmpty(tableMap)) {
            //获取注解值
            for (Class clazz : tableMap.keySet()) {
                //获取注解的值
                TableAnnInfo tableAnnInfo = (TableAnnInfo) tableMap.get(clazz);
                if (!ObjectUtil.isEmpty(tableAnnInfo)) {
                    //获取表名
                    if (StringUtils.isEmpty(tableAnnInfo.getValue())) {
                        if (StringUtils.isEmpty(tableAnnInfo.getTableName())) {
                            //values值和获取类名(大驼峰式)转化为下划线模式
                            tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
                        }
                    } else {
                        tableName = tableAnnInfo.getValue();
                    }
                    if (!StringUtils.isEmpty(tableAnnInfo.getTableName())) {
                        //如果tableName有值
                        tableName = tableAnnInfo.getTableName();
                    }
                    tableAnnInfo.setValue(tableName);
                    tableAnnInfo.setTableName(tableName);
                    //获取列信息
                    tableStructList = getColumnList(tableName, clazz, columMap.get(clazz), keyMap.get(clazz), indexMap.get(clazz));
                    tableInfoMap.put(tableAnnInfo, tableStructList);
                }
            }
        }
        return tableInfoMap;
    }

    private List<TableStruct> getColumnList(String tableName, Class clazz, Map<Field, Object> fieldMap, Map<Field, Object> keyMap, Map<Field, Object> indexMap) {
        TableStruct tableStruct = new TableStruct();
        List<TableStruct> columnList = null;
        TableStruct tableColumn = null;
        //获取数据表类下的所有属性信息
        List<Field> fieldList = autoScann.getStyleCheckFieldQueue().get(clazz);
        if (!CollectionUtils.isEmpty(fieldList)) {
            columnList = new Vector<>();
            for (Field field : fieldList) {
                tableColumn = new TableStruct();
                TableColumnAnnInfo column = null;
                tableColumn.setTableName(tableName);
                tableColumn.setFieldName(field.getName());

                //有Column注解，获取列信息
                if (!CollectionUtils.isEmpty(fieldMap) && !ObjectUtil.isEmpty(fieldMap.get(field))) {
                    column = (TableColumnAnnInfo) fieldMap.get(field);
                    tableColumn.setColumComment(column.getColumComment());
                    tableColumn.setColumName(column.getColumName());
                    tableColumn.setColumnType(column.getColumnType());
                    tableColumn.setDefaultValue(column.getDefaultValue());
                }

                if (ObjectUtil.isEmpty(tableColumn.getColumnType())) {
                    boolean normalType = false;
                    if (StringUtils.isEmpty(tableColumn.getColumName())) {
                        //表的列名取吧属性的小驼峰式转化为下划线式
                        tableColumn.setColumName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()));
                    }

                    if (field.getType().getName().equals(Integer.class.getTypeName()) || field.getType().getName().equals("int")) {
                        tableColumn.setColumnType(ColumnType.INT);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(Boolean.class.getTypeName()) || field.getType().getName().equals("boolean")) {
                        tableColumn.setColumnType(ColumnType.BOOL);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(Byte.class.getTypeName()) || field.getType().getName().equals("byte")) {
                        tableColumn.setColumnType(ColumnType.BYTE);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(Short.class.getTypeName()) || field.getType().getName().equals("boolean")) {
                        tableColumn.setColumnType(ColumnType.BOOL);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(Long.class.getTypeName()) || field.getType().getName().equals("long")) {
                        tableColumn.setColumnType(ColumnType.LONG);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(Character.class.getTypeName()) || field.getType().getName().equals("char")) {
                        tableColumn.setColumnType(ColumnType.MIN_STR);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(Float.class.getTypeName()) || field.getType().getName().equals("float")) {
                        tableColumn.setColumnType(ColumnType.FOLAT);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(Double.class.getTypeName()) || field.getType().getName().equals("double")) {
                        tableColumn.setColumnType(ColumnType.DOUBLE);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(String.class.getTypeName())) {
                        tableColumn.setColumnType(ColumnType.NORMAL_STR);
                        normalType = true;
                    }
                    if (field.getType().getName().equals(Date.class.getTypeName())) {
                        tableColumn.setColumnType(ColumnType.DATE);
                        normalType = true;
                    }
                    if (!normalType) {
                        tableColumn.setColumnType(ColumnType.NORMAL_STR);
                    }

                    //获取键信息
                    if (!CollectionUtils.isEmpty(keyMap) && !ObjectUtil.isEmpty(keyMap.get(field))) {
                        TableKeyAnnInfo tableKeyAnnInfo = (TableKeyAnnInfo) keyMap.get(field);
                        tableColumn.setKeyType(tableKeyAnnInfo.getKeyType());
                        tableColumn.setKeyGrener(tableKeyAnnInfo.getKeyGrener());
                        tableColumn.setGenNum(tableKeyAnnInfo.getGenNum());
                        tableColumn.setGroupNum(tableKeyAnnInfo.getGroupNum());
                    }

                    //获取索引信息
                    if (!CollectionUtils.isEmpty(indexMap) && !ObjectUtil.isEmpty(indexMap.get(field))) {
                        tableColumn.setIndex(true);
                    }
                }
                //获取索引信息
                columnList.add(tableColumn);
            }
        }
        return columnList;
    }


    //创建数据库的表基本信息：表名，字符集，注释等,字符集默认为UTF-8，mysql采用InnerDb引擎
    public String getTableExt(DbType dbType) {
        switch (dbType) {
            case ORACLE:
                return "";
            case MY_SQL:
                return "ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8";
            default:
                return "";
        }
    }


    private static String saveFile(String filePath, String context, String charsetName) {
        File file = new File(filePath);
        File fileParent = null;
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            fileParent = file.getParentFile();//返回的是File类型,可以调用exsit()等方法
            String fileParentPath = file.getParent();//返回的是String类型
            if (!fileParent.exists()) {
                fileParent.mkdirs();// 能创建多级目录
            }
            if (!file.exists()) {
                file.createNewFile();//有路径才能创建文件
            }
            //创建输出流
            fos = new FileOutputStream(file);
            //创建缓冲流
            osw = new OutputStreamWriter(fos);
            if (!"UTF-8".equalsIgnoreCase(charsetName)) {
                context = new String(context.getBytes("UTF-8"), charsetName);
            }
            //对文件进行写入
            osw.write(context, 0, context.length());
            osw.flush();

        } catch (Exception e) {
            log.info("文件操作异常", e);
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                log.info("关闭缓冲流失败", ex);
            }
        }
        return filePath;
    }
}
