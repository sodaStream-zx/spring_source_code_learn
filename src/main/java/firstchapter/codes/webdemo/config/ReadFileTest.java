package firstchapter.codes.webdemo.config;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author zxx
 * @desc 加载jar包中的文件
 * @createTime 2019-11-13-上午 9:34
 */
//@Service
public class ReadFileTest {

    //    @PostConstruct
    public void readResource() throws IOException {
        ClassPathResource cpr = new ClassPathResource("files/shiro.txt");
        InputStream inputStream = cpr.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        inputStream.close();

    }
}
