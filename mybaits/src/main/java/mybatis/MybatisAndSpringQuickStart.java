package mybatis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.mapper.UserMapper;

public class MybatisAndSpringQuickStart {

	public static void main(String[] args) {

		ApplicationContext factory 
		= new ClassPathXmlApplicationContext("spring/dataSource.xml");
		
		UserMapper mapper = (UserMapper) factory.getBean("userMapper");
		System.out.println(mapper.getUser(1).get("SUBJECT"));
		
		
	}

}
