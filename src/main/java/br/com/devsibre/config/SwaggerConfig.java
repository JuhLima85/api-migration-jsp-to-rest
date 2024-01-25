package br.com.devsibre.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI Devsibre() {
		return new OpenAPI().info(new Info()
				.title("Devsibre")
				.description("Segunda Igreja Batista no Recanto das Emas")
				.version("1.0.0")
				.license(new License().name("Sistema de Cadastro - Portal Sibre").url("https://site-sibre.netlify.app/"))
		);
	}
	// --> http://localhost:8080/swagger-ui/index.html
	// http://localhost:8080/swagger-ui.html
}
