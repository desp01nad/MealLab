package gr.unipi.ddim.meallabserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gr.unipi.ddim.meallabapi.api.MealClient;
import gr.unipi.ddim.meallabapi.exceptions.MealApiException;
import gr.unipi.ddim.meallabapi.models.Meal;
import gr.unipi.ddim.meallabserver.dto.MealDetails;
import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.exception.MealNotFoundException;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealClient mealClient;

    @InjectMocks
    private MealService mealService;

    private static Meal mealWithIdAndName(String id, String name) {
        Meal meal = new Meal();
        meal.setIdMeal(id);
        meal.setStrMeal(name);
        return meal;
    }

    @Test
    void searchMeals_ShouldReturnSummaries_WhenMealsFound() {
        // Arrange
        when(mealClient.searchMealsByName("pasta")).thenReturn(List.of(mealWithIdAndName("1", "Penne")));

        // Act
        List<MealSummary> results = mealService.searchMeals("pasta");

        // Assert
        assertEquals(List.of(new MealSummary("1", "Penne", null)), results);
    }

    @Test
    void searchMeals_ShouldReturnEmptyList_WhenNoMealsFound() {
        // Arrange
        when(mealClient.searchMealsByName("rocks")).thenReturn(List.of());

        // Act
        List<MealSummary> results = mealService.searchMeals("rocks");

        // Assert
        assertTrue(results.isEmpty());
    }

    @Test
    void getMealById_ShouldReturnDetails_WhenMealExists() {
        // Arrange
        when(mealClient.getMealById("1")).thenReturn(mealWithIdAndName("1", "Penne"));

        // Act
        MealDetails result = mealService.getMealById("1");

        // Assert
        assertEquals("Penne", result.name());
    }

    @Test
    void getMealById_ShouldThrowNotFound_WhenMealDoesNotExist() {
        // Arrange
        when(mealClient.getMealById("0")).thenReturn(null);

        // Act & Assert
        assertThrows(MealNotFoundException.class, () -> mealService.getMealById("0"));
    }

    @Test
    void searchMeals_ShouldPropagateFailure_WhenMealClientFails() {
        // Arrange
        when(mealClient.searchMealsByName("pasta")).thenThrow(new MealApiException("down"));

        // Act & Assert
        assertThrows(MealApiException.class, () -> mealService.searchMeals("pasta"));
    }
}
