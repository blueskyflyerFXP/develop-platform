package wust.fxp.develop.platfrom.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author 凡兴鹏
 * @create 2021-04-24 17:11
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection collection){
        if(collection==null||collection.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map map){
        if(map==null||map.isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean isEmpty(Object[] objectarr){
        if(objectarr==null||objectarr.length<=0){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(int[] intArr){
        if(intArr==null||intArr.length<=0){
            return true;
        }
        return false;
    }
    public static boolean isEmpty(float[] floatArr){
        if(floatArr==null||floatArr.length<=0){
            return true;
        }
        return false;
    }


}
