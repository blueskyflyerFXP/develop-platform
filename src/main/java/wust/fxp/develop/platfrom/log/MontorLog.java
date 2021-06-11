package wust.fxp.develop.platfrom.log;

import wust.fxp.develop.platfrom.constant.KeyGenerator;

import java.util.UUID;

/**
 * @author 凡兴鹏
 * @create 2021-05-06 20:11
 */
public final class MontorLog {
    public String getSysCode(){
        return UUID.randomUUID().toString();
    }

    public String getSysCode(String patientSysCode){
        return UUID.nameUUIDFromBytes(patientSysCode.getBytes()).toString();
    }
}
