package firstchapter.codes;

import firstchapter.codes.entity.MyUser;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-11-21-13:22
 */
public class ConfigBean {
    public static void main(String[] args) {
        Resource rs = new ClassPathResource("test.xml");
        XmlBeanFactory xb = new XmlBeanFactory(rs);
        MyUser test = xb.getBean("test", MyUser.class);
        System.out.println(test.toString());
    }
}
