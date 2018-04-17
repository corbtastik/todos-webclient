package io.corbs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class TodoClientApp {

    @Value("${client.targetEndpoint}")
    private String targetEndpoint;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(targetEndpoint).build();
    }

	public static void main(String[] args) {
		SpringApplication.run(TodoClientApp.class, args);
	}
}
