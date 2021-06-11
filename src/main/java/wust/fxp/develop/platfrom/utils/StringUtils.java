package wust.fxp.develop.platfrom.utils;

import java.util.Random;

/**
 * @author 凡兴鹏
 * @create 2021-04-24 17:06
 */
public class StringUtils {
    public static boolean isEmpty(String str){
        if(str==null||str.isEmpty()){
            return true;
        }
        return false;
    }
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
