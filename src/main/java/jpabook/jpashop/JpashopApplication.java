package jpabook.jpashop;

import jpabook.jpashop.study.springboot.StartingEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
//		 SpringApplication.run(JpashopApplication.class, args);
		SpringApplication app = new SpringApplication(JpashopApplication.class);
//		app.setWebApplicationType(WebApplicationType.SERVLET);
//		app.addListeners(new StartingEvent());
		app.run(args);
	}

}
