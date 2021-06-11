package wust.fxp.develop.platfrom.constant;

/**
 * 周的定义
 * @author 凡兴鹏
 * @create 2021-04-28 0:15
 */
public enum Week {
    MONDAY(0,1,"Monday","Mon","周一","星期一",false),
    TUESDAY(1,2,"Tuesday","Tes","周二","星期二",false),
    WEDNESDAY(2,3,"Wendesday","Wed","周三","星期三",false),
    THUREDAY(3,4,"Thureday","Thu","周四","星期四",false),
    FIRDAY(4,5,"Firday","Fir","周五","星期五",false),
    SATURDAY(5,6,"Staturday","Sat","周六","星期六",false),
    SUNDAY(6,7,"Monday","Sun","周日","星期天",false);

    Week(int indexNum, int normalNum, String englishName, String englishSimpleName, String chineseName, String chineseAlias, boolean isWorkDay) {
        this.indexNum = indexNum;
        this.normalNum = normalNum;
        this.englishName = englishName;
        this.englishSimpleName = englishSimpleName;
        this.chineseName = chineseName;
        this.chineseAlias = chineseAlias;
    }

    //数字表达
    int indexNum;
    //惯用数字表达
    int normalNum;
    //英文表达
    private String englishName;
    //英文简称
    private String englishSimpleName;
    //中文标准表达
    private String  chineseName;
    //正文日常表达
    private String chineseAlias;
    //是否是工作日
    private boolean isWorkDay;
}
