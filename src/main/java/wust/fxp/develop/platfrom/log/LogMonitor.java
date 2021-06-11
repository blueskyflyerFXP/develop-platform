package wust.fxp.develop.platfrom.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wust.fxp.develop.platfrom.handle.LogHandler;
import wust.fxp.develop.platfrom.utils.JacksonUtils;

import java.util.function.Function;

/**
 * @author 凡兴鹏
 * @create 2021-05-06 20:17
 */
@Slf4j
public class LogMonitor {
    private Function logFun;
    private LogHandler logHandler=new LogHandler();


    public void errorLog(String patientSysCode, String sysCode, String methodCode, Error error, String errorMsg, Object ...params) {
        try {
            Logger logger = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
            Log myLog = new Log();
            myLog.setClassName(Thread.currentThread().getStackTrace()[1].getClassName());
            myLog.setLine(Thread.currentThread().getStackTrace()[1].getLineNumber());
            myLog.setLogLevel("error");
            myLog.setLogType("sysLog");
            myLog.setFileName(Thread.currentThread().getStackTrace()[1].getFileName());
            myLog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            myLog.setLogContext(errorMsg);
            myLog.setThrowable(error);
            myLog.setMethodCode(methodCode);
            myLog.setPatientSysCode(patientSysCode);
            myLog.setSysCode(sysCode);
            myLog.setParams(params);
            //构建日志处理器
            logHandler.handle(myLog, logFun);
        } catch (Exception e) {
            log.info("日志格式化失败", e);
        }
    }

    public void exceptionLog(String patientSysCode, String sysCode, String methodCode, Throwable excepton, String errorMsg, Object ...params) {
        try {
            Logger logger = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
            Log myLog = new Log();
            myLog.setClassName(Thread.currentThread().getStackTrace()[1].getClassName());
            myLog.setLine(Thread.currentThread().getStackTrace()[1].getLineNumber());
            myLog.setLogLevel("error");
            myLog.setLogType("sysLog");
            myLog.setFileName(Thread.currentThread().getStackTrace()[1].getFileName());
            myLog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            myLog.setLogContext(errorMsg);
            myLog.setThrowable(excepton);
            myLog.setMethodCode(methodCode);
            myLog.setPatientSysCode(patientSysCode);
            myLog.setSysCode(sysCode);
            myLog.setParams(params);
            //构建日志处理器
            logHandler.handle(myLog, logFun);
        } catch (Exception e) {
            log.info("日志格式化失败", e);
        }
    }

    public void debugLog(String patientSysCode, String sysCode, String methodCode, Throwable excepton, String errorMsg, Object ...params) {
        try {
            Logger logger = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
            Log myLog = new Log();
            myLog.setClassName(Thread.currentThread().getStackTrace()[1].getClassName());
            myLog.setLine(Thread.currentThread().getStackTrace()[1].getLineNumber());
            myLog.setLogLevel("error");
            myLog.setLogType("sysLog");
            myLog.setFileName(Thread.currentThread().getStackTrace()[1].getFileName());
            myLog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            myLog.setLogContext(errorMsg);
            myLog.setThrowable(excepton);
            myLog.setMethodCode(methodCode);
            myLog.setPatientSysCode(patientSysCode);
            myLog.setSysCode(sysCode);
            myLog.setParams(params);
            //构建日志处理器
            logHandler.handle(myLog, logFun);
        } catch (Exception e) {
            log.info("日志格式化失败", e);
        }
    }
}
