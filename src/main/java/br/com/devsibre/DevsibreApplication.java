package br.com.devsibre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // extends SpringBootServletInitializer
public class DevsibreApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevsibreApplication.class, args);
		// System.out.print(new BCryptPasswordEncoder().encode("santidade777"));
	}

}
