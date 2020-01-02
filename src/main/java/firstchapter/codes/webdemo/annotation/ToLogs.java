package firstchapter.codes.webdemo.annotation;

import java.lang.annotation.*;

/**
 * @author zxx
 * @desc
 * @createTime 2020-01-02-下午 2:39
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ToLogs {
    String value() default "";
}

