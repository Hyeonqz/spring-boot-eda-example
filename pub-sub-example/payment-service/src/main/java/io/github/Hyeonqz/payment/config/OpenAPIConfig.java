package io.github.Hyeonqz.payment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public Info info() {
        return new Info()
                .title("Payment Service API")
                .version("1.0")
                .description("결제 API 문서")
                ;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(info());
    }
}
