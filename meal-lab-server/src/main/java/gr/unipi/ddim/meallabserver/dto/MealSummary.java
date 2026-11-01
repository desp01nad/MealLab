package gr.unipi.ddim.meallabserver.dto;

import gr.unipi.ddim.meallabapi.models.Meal;

public record MealSummary(String id, String name, String thumbnailUrl) {

	public static MealSummary from(Meal meal) {
		return new MealSummary(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
	}
}
