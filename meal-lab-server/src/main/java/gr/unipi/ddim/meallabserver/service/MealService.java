package gr.unipi.ddim.meallabserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gr.unipi.ddim.meallabapi.api.MealClient;
import gr.unipi.ddim.meallabapi.models.Meal;
import gr.unipi.ddim.meallabserver.dto.MealDetails;
import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.exception.MealNotFoundException;

@Service
public class MealService {

    private final MealClient mealClient;

    public MealService(MealClient mealClient) {
        this.mealClient = mealClient;
    }

    public List<MealSummary> searchMeals(String name) {
        return mealClient.searchMealsByName(name).stream()
                .map(MealSummary::from)
                .toList();
    }

    public MealDetails getMealById(String id) {
        Meal meal = mealClient.getMealById(id);
        if (meal == null) {
            throw new MealNotFoundException(id);
        }
        return MealDetails.from(meal);
    }

    public MealDetails getRandomMeal() {
        return MealDetails.from(mealClient.getRandomMeal());
    }
}
