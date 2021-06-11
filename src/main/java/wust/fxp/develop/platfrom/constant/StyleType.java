package wust.fxp.develop.platfrom.constant;

/**
 * @author 凡兴鹏
 * @create 2021-04-28 9:19
 */
public enum StyleType {
    //小驼峰式
    LITTLE_HUMP("little_hump","^[a-z]{1,}([A-Z][a-z]{0,})*","小驼峰式"),
    //大坨峰式
    BIG_HUMP("big_hump","([A-Z][a-z]{1,}){1,}","大驼峰式"),
    //下划线式
    UNDER_LINE("under_line","^[a-z]{1,}(_[a-z]{1,})*","下划线式"),
    //中横线式
    MIDDLE_LINE("middle_line","^[a-z]{1,}(-([a-z]{1,})*","中横线式"),
    //前缀式（除了前缀为大驼峰式）
    PREFIX("prefix","^([a-z]|[A-Z]){1,}([A-Z][a-z]*)*","前缀式"),
    //自定义
    CUSTOMIZE("customize","^[A-Z][a-z]{1,}([A-Z]{0,})*","自定义","默认为大驼峰式")
;

    StyleType(String ruleName, String ruleContext, String ruleDesc) {
        this.ruleName = ruleName;
        this.ruleContext = ruleContext;
        this.ruleDesc = ruleDesc;
    }

    StyleType(String ruleName, String ruleContext, String ruleDesc, String ext) {
        this.ruleName = ruleName;
        this.ruleContext = ruleContext;
        this.ruleDesc = ruleDesc;
        this.ext = ext;
    }

    //规则名称
    private String ruleName;
    //规则内容
    private String ruleContext;
    //规则描述
    private String ruleDesc;
    //规则拓展内容
    private String ext;

    public boolean checkStyle(String checkStr){
        return checkStr.matches(this.ruleContext);
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleContext() {
        return ruleContext;
    }

    public void setRuleContext(String ruleContext) {
        this.ruleContext = ruleContext;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
