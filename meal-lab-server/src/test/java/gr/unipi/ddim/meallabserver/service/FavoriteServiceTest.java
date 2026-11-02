package gr.unipi.ddim.meallabserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.entity.Favorite;
import gr.unipi.ddim.meallabserver.entity.StoredMeal;
import gr.unipi.ddim.meallabserver.exception.MealNotFoundException;
import gr.unipi.ddim.meallabserver.repository.FavoriteRepository;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    private static final StoredMeal PENNE = new StoredMeal("1", "Penne", "thumb.jpg");

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private StoredMealService storedMealService;

    @InjectMocks
    private FavoriteService favoriteService;

    @Test
    void addFavorite_ShouldSaveFavorite_WhenNotAlreadyFavorite() {
        // Arrange
        when(favoriteRepository.existsByMealId("1")).thenReturn(false);
        when(storedMealService.findOrFetchMeal("1")).thenReturn(PENNE);

        // Act
        favoriteService.addFavorite("1");

        // Assert
        ArgumentCaptor<Favorite> captor = ArgumentCaptor.forClass(Favorite.class);
        verify(favoriteRepository).save(captor.capture());
        assertEquals(PENNE, captor.getValue().getMeal());
    }

    @Test
    void addFavorite_ShouldDoNothing_WhenAlreadyFavorite() {
        // Arrange
        when(favoriteRepository.existsByMealId("1")).thenReturn(true);

        // Act
        favoriteService.addFavorite("1");

        // Assert
        verify(favoriteRepository, never()).save(any());
    }

    @Test
    void addFavorite_ShouldNotSave_WhenMealDoesNotExist() {
        // Arrange
        when(favoriteRepository.existsByMealId("0")).thenReturn(false);
        when(storedMealService.findOrFetchMeal("0")).thenThrow(new MealNotFoundException("0"));

        // Act & Assert
        assertThrows(MealNotFoundException.class, () -> favoriteService.addFavorite("0"));
        verify(favoriteRepository, never()).save(any());
    }

    @Test
    void removeFavorite_ShouldDeleteByMealId() {
        // Act
        favoriteService.removeFavorite("1");

        // Assert
        verify(favoriteRepository).deleteByMealId("1");
    }

    @Test
    void getFavorites_ShouldReturnSummaries() {
        // Arrange
        when(favoriteRepository.findAll()).thenReturn(List.of(new Favorite(PENNE)));

        // Act
        List<MealSummary> results = favoriteService.getFavorites();

        // Assert
        assertEquals(List.of(new MealSummary("1", "Penne", "thumb.jpg")), results);
    }
}
