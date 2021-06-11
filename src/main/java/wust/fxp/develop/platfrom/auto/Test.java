package wust.fxp.develop.platfrom.auto;


import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.Map;

public class Test {

    public void test() throws Exception {

        //设置扫描的包和生成表结构的存储目录
        AutoGen autoGen=AutoGen.genStart(File.separator+"src"+File.separator+"main"+File.separator+"java","wust.fxp.develop.platfrom","../");
        //生成表结构的建表语句和简单CRUD语句
        Map tableMap=autoGen.getTableStruct();



       /* System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));//testData
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));//testData
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));//TestData
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testdata"));//testdata
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));//test_data
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));//test-data*/


    }

    public static void main(String[] args) throws Exception {
        new Test().test();
    }
}
