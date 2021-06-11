package wust.fxp.develop.platfrom.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * @author 凡兴鹏
 * @create 2021-04-26 17:47
 */
public class ConfigUtil {
    @Autowired
    private static Environment env;

    public static String getConfig(String configName){
        return env.getProperty(configName);
    }

    public static Object getConfig(String configName,Class clazz){
        return env.getProperty(configName,clazz);
    }

}
