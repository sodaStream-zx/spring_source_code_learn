package firstChapter.codes.entity;

/**
 * @author zxx
 * @desc
 * @createTime 2019-12-12-下午 5:02
 */
public class MyUser {
    private String uName;
    private String uPwd;
    private Integer uAge;

    @Override
    public String toString() {
        return "MyUser{" +
                "uName='" + uName + '\'' +
                ", uPwd='" + uPwd + '\'' +
                ", uAge=" + uAge +
                '}';
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public Integer getuAge() {
        return uAge;
    }

    public void setuAge(Integer uAge) {
        this.uAge = uAge;
    }
}
