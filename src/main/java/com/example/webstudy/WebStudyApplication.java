package com.example.webstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing	//최소 하나의 JPA @Entity 클래스가 필요하다.
@SpringBootApplication	//스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 자동으로 설정해준다. 이 어노테이션이 있는 위치부터 읽어가기 때문에 최상단에 위치해야 한다.
public class WebStudyApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebStudyApplication.class, args);	// .run으로 인해 내장 WAS가 서버를 실행한다. JAR 파일로 실행 가능하다.
	}

}
