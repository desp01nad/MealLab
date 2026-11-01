package gr.unipi.ddim.meallabserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gr.unipi.ddim.meallabapi.api.MealClient;
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
}
