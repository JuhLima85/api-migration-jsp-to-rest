package br.com.devsibre.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI Devsibre() {
		return new OpenAPI().info(new Info()
				.title("Devsibre")
				.description("Portal de cadastros")
				.version("1.0.0"));
	}
	// --> http://localhost:8080/swagger-ui/index.html
}
