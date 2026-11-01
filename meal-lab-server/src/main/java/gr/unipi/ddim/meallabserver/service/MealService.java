package gr.unipi.ddim.meallabserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gr.unipi.ddim.meallabapi.api.MealClient;
import gr.unipi.ddim.meallabserver.dto.MealDetails;
import gr.unipi.ddim.meallabserver.dto.MealSummary;

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
        return MealDetails.from(mealClient.getMealById(id));
    }

    public MealDetails getRandomMeal() {
        return MealDetails.from(mealClient.getRandomMeal());
    }
}
