package cn.ydlan.di;

/**
 * 创建方法并添加注解
 */
public class DIDemo {
    private String str;

    @Autowired
    public void setStr (String str) {
        this.str = str;
    }

    public String getStr () {
        return str;
    }
}
