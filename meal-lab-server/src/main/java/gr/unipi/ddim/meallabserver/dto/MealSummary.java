package gr.unipi.ddim.meallabserver.dto;

import gr.unipi.ddim.meallabapi.models.Meal;
import gr.unipi.ddim.meallabserver.entity.StoredMeal;

public record MealSummary(String id, String name, String thumbnailUrl) {

	public static MealSummary from(Meal meal) {
		return new MealSummary(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
	}

	public static MealSummary from(StoredMeal meal) {
		return new MealSummary(meal.getId(), meal.getName(), meal.getThumbnailUrl());
	}

}
