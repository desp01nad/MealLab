package gr.unipi.ddim.meallabserver.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gr.unipi.ddim.meallabapi.api.MealClient;

@Configuration
@EnableConfigurationProperties(MealDbProperties.class)
public class MealClientConfig {

	@Bean
	public MealClient mealClient(MealDbProperties properties) {
		return new MealClient(properties.baseUrl());
	}
}
