package gr.unipi.ddim.meallabserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gr.unipi.ddim.meallabapi.api.MealClient;
import gr.unipi.ddim.meallabapi.models.Meal;

@Service
public class MealService {

    private final MealClient mealClient;

    public MealService(MealClient mealClient) {
        this.mealClient = mealClient;
    }

    public List<Meal> searchMeals(String name) {
        return mealClient.searchMealsByName(name);
    }
}
