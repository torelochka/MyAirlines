package ru.itis.zheleznov.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.zheleznov.impl.SpringAirlinesImplApplication;

@SpringBootApplication
@Import(SpringAirlinesImplApplication.class)
public class SpringAirlinesWebApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringAirlinesWebApplication.class, args);
	}

}
