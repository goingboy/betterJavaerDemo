package top.codingmore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.codingmore.mpg.mapper")
public class CodingmoreStudyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingmoreStudyDemoApplication.class, args);
	}

}
