package gr.unipi.ddim.meallabserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties("mealdb")
@Validated
public record MealDbProperties(@NotBlank String baseUrl) {
}
