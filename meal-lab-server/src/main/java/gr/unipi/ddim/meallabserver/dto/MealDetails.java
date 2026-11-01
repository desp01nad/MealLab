package gr.unipi.ddim.meallabserver.dto;

import java.util.List;

import gr.unipi.ddim.meallabapi.models.Meal;

public record MealDetails(String id, String name, String category, String area, String instructions,
		String thumbnailUrl, String youtubeUrl, List<String> ingredients) {

	public static MealDetails from(Meal meal) {
		List<String> ingredients = meal.getIngredientsList().stream()
				.map(String::strip)
				.toList();

		return new MealDetails(meal.getIdMeal(), meal.getStrMeal(), meal.getStrCategory(), meal.getStrArea(),
				meal.getStrInstructions(), meal.getStrMealThumb(), meal.getStrYoutube(), ingredients);
	}
}
