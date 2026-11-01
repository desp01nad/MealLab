package gr.unipi.ddim.meallabserver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.unipi.ddim.meallabapi.api.MealClient;
import gr.unipi.ddim.meallabapi.models.Meal;
import gr.unipi.ddim.meallabserver.entity.StoredMeal;
import gr.unipi.ddim.meallabserver.exception.MealNotFoundException;
import gr.unipi.ddim.meallabserver.repository.StoredMealRepository;

@Service
public class StoredMealService {

    private final StoredMealRepository storedMealRepository;
    private final MealClient mealClient;

    public StoredMealService(StoredMealRepository storedMealRepository, MealClient mealClient) {
        this.storedMealRepository = storedMealRepository;
        this.mealClient = mealClient;
    }

    @Transactional
    public StoredMeal findOrFetchMeal(String mealId) {
        return storedMealRepository.findById(mealId).orElseGet(() -> fetchAndStoreMeal(mealId));
    }

    private StoredMeal fetchAndStoreMeal(String mealId) {
        Meal meal = mealClient.getMealById(mealId);
        if (meal == null) {
            throw new MealNotFoundException(mealId);
        }
        return storedMealRepository.save(new StoredMeal(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb()));
    }
}
