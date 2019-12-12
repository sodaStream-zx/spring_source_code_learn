package firstchapter.codes.webdemo.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.LinkedHashMap;

//@Aspect
//@Component//定义一个切面
public class LogRecordAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);
    private static final String UTF_8 = "utf-8";
    public final String string = "execution(*  *..*.*.controllers..*.*(..))";

    // 定义切点Pointcut
    @Pointcut(value = string)
    public void excuteService() {
    }

//    //执行切点 之前
//    @Before("excudeService()")
//    public void exBefore(JoinPoint pjp) {
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//        Long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime);
//    }

    // 通知（环绕）
    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
//        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = "";
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        try {
            //获取请求参数集合并进行遍历拼接
            if (args.length > 0) {
                if ("POST".equals(method)) {
                    Object object = args[0];
                    params = JSON.toJSONString(object);
                } else if ("GET".equals(method)) {
                    params = queryString;
                }
                if (!StringUtils.isEmpty(params)) {
                    params = URLDecoder.decode(params, UTF_8);
                }
            }
            LinkedHashMap<String, Object> mp = new LinkedHashMap<>();
            mp.put("请求地址", uri);
            mp.put("请求参数", params);
            mp.put("处理时间", (endTime - startTime) + "ms");
            mp.put("请求类型", method);
            logger.warn(JSON.toJSONString(mp));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("log error !! {}", e);
        }
        return result;
    }


    //    执行切点之后
    @After("excudeService()")
    public void exAfter(JoinPoint joinPoint) {
    }

}
