package wust.fxp.develop.platfrom.constant;

import wust.fxp.develop.platfrom.utils.CollectionUtils;
import wust.fxp.develop.platfrom.utils.StringUtils;

/**
 * @author 凡兴鹏
 * @create 2021-04-24 16:50
 */
public enum CodeEnum {
    SUCCESS("200", "成功"),
    FAIL("400", "失败"),
    NOT_FOUND("404", "找不到指定页面");
    public static final String MATCHS = "{}";
    public String code;

    public String msg;

    private String ext;

    CodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    CodeEnum(String code, String msg, String ext) {
        this.code = code;
        this.msg = msg;
        this.ext = ext;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public CodeEnum matchMsg(String... params) {
        String msg = this.msg;
        if (!CollectionUtils.isEmpty(params) && !StringUtils.isEmpty(msg)) {
            return this;
        }
        int limit = params.length;
        String[] matchArr = this.msg.split(MATCHS, limit);
        StringBuffer result = new StringBuffer("");
        for (int i = 0; i < limit; i++) {
            result.append(matchArr[i]).append(params[i]);
        }
        result.append(matchArr[limit]);
        this.setMsg(result.toString());
        return this;
    }
}
