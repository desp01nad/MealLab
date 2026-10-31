package gr.unipi.ddim.meallabserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gr.unipi.ddim.meallabapi.api.MealClient;

@Configuration
public class MealClientConfig {

	@Bean
	public MealClient mealClient() {
		return new MealClient();
	}
}
