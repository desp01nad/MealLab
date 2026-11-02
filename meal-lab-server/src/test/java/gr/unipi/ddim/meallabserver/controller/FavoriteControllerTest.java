package gr.unipi.ddim.meallabserver.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.exception.MealNotFoundException;
import gr.unipi.ddim.meallabserver.service.FavoriteService;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FavoriteService favoriteService;

    @Test
    void addFavorite_ShouldReturn204_WhenMealIdIsValid() throws Exception {
        mockMvc.perform(put("/api/favorites/1"))
                .andExpect(status().isNoContent());

        verify(favoriteService).addFavorite("1");
    }

    @Test
    void addFavorite_ShouldReturn400_WhenMealIdIsNotNumeric() throws Exception {
        mockMvc.perform(put("/api/favorites/abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(favoriteService);
    }

    @Test
    void addFavorite_ShouldReturn404_WhenMealDoesNotExist() throws Exception {
        doThrow(new MealNotFoundException("0")).when(favoriteService).addFavorite("0");

        mockMvc.perform(put("/api/favorites/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeFavorite_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/favorites/1"))
                .andExpect(status().isNoContent());

        verify(favoriteService).removeFavorite("1");
    }

    @Test
    void getFavorites_ShouldReturn200WithFavorites() throws Exception {
        when(favoriteService.getFavorites()).thenReturn(List.of(new MealSummary("1", "Penne", "thumb.jpg")));

        mockMvc.perform(get("/api/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Penne"));
    }
}
