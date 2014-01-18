package mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface FirstMybatisMapper {
	
	public Map<String, Object> queryMailSettings(Map<String,Object> param);
	
	public List<Map<String,Object>> executeSQL(Map<String,Object> param);
	
	@Select("Select * from user where id = #{id}")
	public Map<String,Object> testAnnotation(Map<String,Object> param);
	
	public List<Map<String,Object>> queryByList(Map<String,Object>param);
}
