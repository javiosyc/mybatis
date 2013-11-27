package spring.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
	@Select("SELECT * FROM user WHERE id = #{userId}")
	public Map<String, Object> getUser(@Param("userId") int userId);
}
