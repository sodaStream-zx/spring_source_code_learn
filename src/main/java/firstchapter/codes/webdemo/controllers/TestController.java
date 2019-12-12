package firstchapter.codes.webdemo.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import firstchapter.codes.webdemo.entity.SysRole;
import firstchapter.codes.webdemo.services.TestService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-06-16-14:00
 */
@RestController
@RequestMapping(value = "/data")
public class TestController {
    private static final Logger log = Logger.getLogger(TestController.class);
    private static String[] name = new String[]{"张三", "李四", "王二", "血不染", "持之不败", "随心不欲"};
    private static String[] types = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
    @Autowired
    private TestService testService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/insert")
    public String toInsert(Integer count, SysRole sysRole1) {
        long st = System.currentTimeMillis();
        SysRole sysRole = null;
        if (count == null) {
            return "ok";
        }
        for (int i = 0; i < count; i++) {
            sysRole = new SysRole();
            sysRole.setRole_name(name[new Random().nextInt(6)]);
            sysRole.setEnabled(new Random().nextInt(2));
            sysRole.setCreate_time(LocalDateTime.now().minusMonths(i).format(DateTimeFormatter.ISO_DATE));
            sysRole.setCreate_by((long) i);
            testService.insertOne(sysRole);
        }
        log.warn((System.currentTimeMillis() - st));
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("用时", System.currentTimeMillis() - st);
        map.put("model", sysRole);
        return JSON.toJSONString(map, SerializerFeature.PrettyFormat);
    }

    /**
     * 文件上传具体实现方法;
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public String handleFileUpload(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String path = System.getProperty("user.dir") + "/myfiles/";
                File picPath = new File(path);
                if (!picPath.exists()) {
                    picPath.mkdirs();
                }
                File destination = new File(path + file.getOriginalFilename());
                if (!destination.exists()) {
                    destination.createNewFile();
                }
                log.warn("canWrite :" + destination.canWrite());
                log.warn("canRead:" + destination.canRead());
                log.warn("canExecute:" + destination.canExecute());
                log.warn("path:" + destination.getPath());
                FileUtils.copyToFile(file.getInputStream(), destination);
//                file.transferTo(destination);
            } catch (IOException e) {
                log.error("上传文件异常 {}", e);
                return "上传失败," + e.getMessage();
            }
            return "上传成功";
        } else {
            return "上传失败，因为文件是空的.";
        }
    }

    @GetMapping(value = "/one", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getImage() throws IOException {
        String path = System.getProperty("user.dir") + "/files/";
        File file = new File(path + "QQ图片20180927181736.png");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return bytes;
    }

    @GetMapping(value = "/many")
    @ResponseBody
    public Map<String, String> getImages() throws IOException {
        String path = System.getProperty("user.dir") + "/files/";
        File file = new File(path + "辐射4年度版1920x1080壁纸_彼岸图网.jpg");
        File file2 = new File(path + "夜晚 星星 星空 梦幻 4K壁纸_彼岸图网.jpg");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        byte[] bytes2 = FileUtils.readFileToByteArray(file2);
        String s = Base64Utils.encodeToString(bytes);
        String s2 = Base64Utils.encodeToString(bytes2);
        Map<String, String> bufferedImages = new HashMap<>();
        bufferedImages.put("img", s);
        bufferedImages.put("img2", s2);
        return bufferedImages;
    }

    @GetMapping(value = "/redis")
    public void myTest() throws InterruptedException {
        ThreadPoolExecutor ex = new ThreadPoolExecutor(10, 20, 500, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1));

        for (int j = 0; j < 50; j++) {
            for (int i = 0; i < 9; i++) {
                int finalj = j;
                ex.execute(() -> {
                    for (int k = 0; k < 500; k++) {
                        int i1 = new Random().nextInt(8);
                        this.incr(types[i1], finalj);
                    }
                });
            }
            int finalJ1 = j;
            ex.execute(() -> {
                for (int i = 0; i < 50; i++) {
                    Map map = this.get(finalJ1);
                    System.out.println(map);
                    System.out.println("------------------");
                }
            });
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public void incr(String type, Integer num) {
        redisTemplate.opsForHash().increment(num + "-map", type, 1);
    }

    public Map get(Integer num) {
        return redisTemplate.opsForHash().entries(num + "-map");
    }
}
