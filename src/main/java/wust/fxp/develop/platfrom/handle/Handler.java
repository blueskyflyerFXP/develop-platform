package wust.fxp.develop.platfrom.handle;

import java.util.function.Function;

/**
 * 用于设置处理的入参和处理器
 * @author 凡兴鹏
 * @create 2021-05-05 23:23
 */
public interface Handler {
  default   Object handle(Object handleContext, Function function){
      return function.apply(handleContext);
  }
}
