package firstchapter.codes.webdemo.entity;

/**
 * @author zxx
 * @desc
 * @createTime 2019-11-12-下午 1:36
 */
public class HashTest {

    private Long id;
    private Integer hashv;
    private String url;


    public HashTest(Integer hashv, String url) {
        this.hashv = hashv;
        this.url = url;
    }

    @Override
    public String toString() {
        return "HashTest{" +
                "id=" + id +
                ", hashv='" + hashv + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHashv() {
        return hashv;
    }

    public void setHashv(Integer hashv) {
        this.hashv = hashv;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
