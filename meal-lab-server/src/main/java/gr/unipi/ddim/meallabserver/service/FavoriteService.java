package gr.unipi.ddim.meallabserver.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.entity.Favorite;
import gr.unipi.ddim.meallabserver.entity.StoredMeal;
import gr.unipi.ddim.meallabserver.repository.FavoriteRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final StoredMealService storedMealService;

    public FavoriteService(FavoriteRepository favoriteRepository, StoredMealService storedMealService) {
        this.favoriteRepository = favoriteRepository;
        this.storedMealService = storedMealService;
    }

    @Transactional
    public void addFavorite(String mealId) {
        if (favoriteRepository.existsByMealId(mealId)) {
            return;
        }
        StoredMeal meal = storedMealService.findOrFetchMeal(mealId);
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
}
