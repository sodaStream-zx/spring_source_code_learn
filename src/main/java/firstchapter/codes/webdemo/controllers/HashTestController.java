package firstchapter.codes.webdemo.controllers;

import firstchapter.codes.webdemo.services.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author zxx
 * @desc
 * @createTime 2019-11-12-下午 1:39
 */
@RestController
@RequestMapping(value = "/test")
public class HashTestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestService testService;

    @GetMapping(value = "/hash")
    public void inserth() {
        List<String> urls = testService.urls();
        urls.forEach(s -> {
            testService.insertHash(s.hashCode(), s);
            log.warn("insert {}", s);
        });
    }

    @GetMapping(value = "/find")
    public HashMap findAv(String hash, String url) {
        HashMap<String, Long> stringLong = new HashMap<>();
        if (!StringUtils.isEmpty(hash)) {
            long st = System.currentTimeMillis();
            List<Integer> byHash = testService.findByHash(hash);
            stringLong.put("hash cost", (System.currentTimeMillis() - st));
            System.out.println("hashv:" + byHash);
        }
        if (!StringUtils.isEmpty(url)) {
            long st = System.currentTimeMillis();
            List<String> byUrl = testService.findByUrl(url);
            stringLong.put("url cost ", (System.currentTimeMillis() - st));
            System.out.println("url:" + byUrl);
        }
        return stringLong;
    }

    @GetMapping(value = "/hashv")
    public List<Integer> hashv(String hash) {
        return testService.findByHash(hash);
    }

    @GetMapping(value = "/url")
    public List<String> url(String url) {
        return testService.findByUrl(url);
    }

    @GetMapping(value = "/str")
    public String str() {
        return "this is a ajax string";
    }
}
