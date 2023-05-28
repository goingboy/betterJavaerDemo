package top.codingmore.generator;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import top.codingmore.mysqlAndDruid.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@SpringBootTest
@Slf4j
public class CodingmoreStudyDemoApplicationTests {
	@Autowired
	JdbcTemplate jdbcTemplate;
	//使用SLf4j注解替代
//	static Logger logger = LoggerFactory.getLogger(CodingmoreStudyDemoApplication.class);

	@Test
	void contextLoads() {
		LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);

		//看不到这行日志，因为日志级别： TRACE < DEBUG < INFO <  WARN < ERROR

		log.info("logback testing ...");

		log.debug("沉默王二是傻 X");
		log.info("周少雄 是大傻 X");
		System.out.println(System.getProperty("user.home"));

	}

	@Test
	void MysqlTest(){
		String sql = "select * from user";
		List<User> users = jdbcTemplate.query(sql, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setAge(rs.getInt("age"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("name"));
				return user;
			}
		});

		log.info("查询成功：users={}", users);

	}
}
