package mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class FirstMybatis {

	public static void main(String[] args) throws IOException, SQLException {

		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);

		final String ENV = "test";

		// 1.using test env SqlSessionFactoryBuilder create SqlSessionFactory
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(
				inputStream, ENV);

		SqlSession session = factory.openSession();

		System.out.println(session.getConfiguration().getEnvironment().getDataSource().getConnection());

		try {

			// 2.using mapper
			FirstMybatisMapper mapper = session
					.getMapper(mybatis.FirstMybatisMapper.class);

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", 1);
			
			// 2.1 param
			Map<String, Object> map = mapper.queryMailSettings(param);

			showMailSetting(map);
			String sql = map.get("sql").toString();

			param.clear();
			param.put("sql", sql);

			// 2.2 dynamic sql
			List<Map<String, Object>> exeuteResult = mapper.executeSQL(param);
			System.out.println(exeuteResult.get(0).get("id"));

			//2.3. using annotation
			param.clear();
			param.put("id", 2);
			Map<String, Object> result = mapper.testAnnotation(param);
			System.out.println(result.get("id"));

			//2.4. using list param
			param.clear();
			List<String> idList = new ArrayList<String>();
			idList.add("1");
			idList.add("2");
			param.put("idList", idList);
			List<Map<String,Object>> users = mapper.queryByList(param);
			System.out.println(users.get(1).get("id"));
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			session.close();
		}

	}

	private static void showMailSetting(Map<String, Object> map) {
		
		System.out.println(map.get(MailSetting.CC.getDBColumnName()));
		System.out.println(map.get(MailSetting.BCC.getDBColumnName()));
		System.out.println(map.get(MailSetting.CONTEXT.getDBColumnName()));
		System.out.println(map.get(MailSetting.ENV.getDBColumnName()));

	}

	private enum MailSetting {
		CC("CC","CC"), BCC("BCC","BCC"), CONTEXT("CONTEXT","CONTEXT"),
		ENV("","ENV"), RECEIVER("email","RECEIVER");

		private String emailTitle;
		private String dbColumnName;
		MailSetting(String emailTitle,String dbColumnName) {
			this.emailTitle = emailTitle;
			this.dbColumnName = dbColumnName;
		}

		public String getEmailTitle() {
			return this.emailTitle;
		}
		public String getDBColumnName(){
			return this.dbColumnName;
		}
	}
}
