package firstchapter.codes.webdemo.mappers;


import firstchapter.codes.webdemo.entity.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-06-16-14:00
 */
@Mapper
public interface TestMapper {

    @Insert(value = "insert into sys_role (role_name,enabled,create_time,create_by) values (" +
            "#{u.role_name},#{u.enabled},#{u.create_time},#{u.create_by}" +
            ")")
    Integer insert(@Param(value = "u") SysRole u);

    @Insert(value = "insert into hashtest(hashv,url) values(#{hashv},#{url})")
    Integer insertHash(@Param(value = "hashv") Integer hashv,
                       @Param(value = "url") String url);

    @Select(value = "select url from hashtest where url = #{url}")
    List<String> findByUrl(@Param(value = "url") String url);

    @Select(value = "select hashv from hashtest where hashv = #{hash}")
    List<Integer> findByHash(@Param(value = "hash") String hash);

    @Select(value = "SELECT url from t_data_info limit 502933,10000000")
    List<String> urls();
}
