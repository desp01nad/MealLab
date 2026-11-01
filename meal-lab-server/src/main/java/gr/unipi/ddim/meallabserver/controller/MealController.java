package gr.unipi.ddim.meallabserver.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.unipi.ddim.meallabapi.api.MealClient;
import gr.unipi.ddim.meallabapi.models.Meal;

@RestController
@RequestMapping("/api/meals")
public class MealController {

	private final MealClient mealClient;

	public MealController(MealClient mealClient) {
		this.mealClient = mealClient;
	}

	@GetMapping
	public List<Meal> searchMeals(@RequestParam String name) {
		return mealClient.searchMealsByName(name);
	}
}