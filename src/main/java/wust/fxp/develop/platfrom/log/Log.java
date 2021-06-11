package wust.fxp.develop.platfrom.log;

import lombok.Data;
import wust.fxp.develop.platfrom.annotation.Table;

import java.util.Date;

/**
 * 用来格式化日志信息
 * @author 凡兴鹏
 * @create 2021-05-05 23:21
 */
@Table
@Data
public class Log {
    //日志等级
    private String logLevel;
    //日志类型：基础日志，研发系统日志，自定义日志
    private String logType;
    //上级追踪号
    private String patientSysCode;
    //追踪号
    private String sysCode;
    //接口编码
    private String methodCode;
    //时间
    private String timestamp;
    //内容
    private String logContext;
    //线程
    private String theadId;
    //类
    private String className;
    //方法
    private String methodName;
    //文件
    private String fileName;
    //行数
    private int line;
    //异常信息
    private Throwable throwable;
    //其他参数
    private Object[] params;
}
