package wust.fxp.develop.platfrom.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 凡兴鹏
 * @create 2021-04-24 18:44
 */
public class DateUtils {

    /**年月日*/
    public static final String YMD="yyyy-MM-dd";
    /**年月日 时分秒*/
    public static final String YMDHMS="yyyy-MM-dd hh:mm:ss";
    /**时分秒*/
    public static final String  HMS="hh:mm:ss";
    /**时分*/
    public static final String  HM="hh:mm";
    /**年月日 时分秒 毫秒*/
    public static final String YMDHMSS="yyyy-MM-dd hh:mm:ss -S";


    public static String getNowDate(String formatStr){
        SimpleDateFormat dateFormat=new SimpleDateFormat(formatStr);
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }
}
