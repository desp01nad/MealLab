package gr.unipi.ddim.meallabserver.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.unipi.ddim.meallabapi.api.MealClient;
import gr.unipi.ddim.meallabapi.models.Meal;
import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.entity.Favorite;
import gr.unipi.ddim.meallabserver.entity.StoredMeal;
import gr.unipi.ddim.meallabserver.exception.MealNotFoundException;
import gr.unipi.ddim.meallabserver.repository.FavoriteRepository;
import gr.unipi.ddim.meallabserver.repository.StoredMealRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final StoredMealRepository storedMealRepository;
    private final MealClient mealClient;

    public FavoriteService(FavoriteRepository favoriteRepository, StoredMealRepository storedMealRepository,
            MealClient mealClient) {
        this.favoriteRepository = favoriteRepository;
        this.storedMealRepository = storedMealRepository;
        this.mealClient = mealClient;
    }

    @Transactional
    public void addFavorite(String mealId) {
        if (favoriteRepository.existsByMealId(mealId)) {
            return;
        }
        StoredMeal meal = storedMealRepository.findById(mealId).orElseGet(() -> fetchAndStoreMeal(mealId));
        favoriteRepository.save(new Favorite(meal));
    }

    @Transactional
    public void removeFavorite(String mealId) {
        favoriteRepository.deleteByMealId(mealId);
    }

    @Transactional(readOnly = true)
    public List<MealSummary> getFavorites() {
        return favoriteRepository.findAll().stream()
                .map(favorite -> MealSummary.from(favorite.getMeal()))
                .toList();
    }

    private StoredMeal fetchAndStoreMeal(String mealId) {
        Meal meal = mealClient.getMealById(mealId);
        if (meal == null) {
            throw new MealNotFoundException(mealId);
        }
        return storedMealRepository.save(new StoredMeal(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb()));
    }
}
