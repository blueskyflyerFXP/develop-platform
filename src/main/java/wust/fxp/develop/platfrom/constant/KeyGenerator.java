package wust.fxp.develop.platfrom.constant;

/**
 * @author 凡兴鹏
 * @create 2021-04-27 23:04
 */
public enum KeyGenerator {
    By_RANDOM("by_random"),
    BY_UUID("by_uuid"),
    BY_TIME("by_time"),
    BY_COUNT("by_count");

    KeyGenerator(String genName) {
        this.genName = genName;
    }

    private String genName;

    public String getGenName() {
        return genName;
    }

    public void setGenName(String genName) {
        this.genName = genName;
    }
}
