package gr.unipi.ddim.meallabserver.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import gr.unipi.ddim.meallabapi.exceptions.MealApiException;
import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.exception.MealNotFoundException;
import gr.unipi.ddim.meallabserver.service.MealService;

@WebMvcTest(MealController.class)
class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MealService mealService;

    @Test
    void searchMeals_ShouldReturn200WithResults_WhenNameIsValid() throws Exception {
        when(mealService.searchMeals("pasta")).thenReturn(List.of(new MealSummary("1", "Penne", "thumb.jpg")));

        mockMvc.perform(get("/api/meals").param("name", "pasta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Penne"))
                .andExpect(jsonPath("$[0].strMeal").doesNotExist());
    }

    @Test
    void searchMeals_ShouldReturn400_WhenNameIsBlank() throws Exception {
        mockMvc.perform(get("/api/meals").param("name", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        verifyNoInteractions(mealService);
    }

    @Test
    void searchMeals_ShouldReturn400_WhenNameIsMissing() throws Exception {
        mockMvc.perform(get("/api/meals"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMealById_ShouldReturn400_WhenIdIsNotNumeric() throws Exception {
        mockMvc.perform(get("/api/meals/abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mealService);
    }

    @Test
    void getMealById_ShouldReturn404_WhenMealDoesNotExist() throws Exception {
        when(mealService.getMealById("0")).thenThrow(new MealNotFoundException("0"));

        mockMvc.perform(get("/api/meals/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("No meal found with id 0"));
    }

    @Test
    void searchMeals_ShouldReturn502_WhenMealApiFails() throws Exception {
        when(mealService.searchMeals("pasta")).thenThrow(new MealApiException("down"));

        mockMvc.perform(get("/api/meals").param("name", "pasta"))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.detail").value("The recipe service is unavailable"));
    }

    @Test
    void searchMeals_ShouldReturn500WithoutInternalDetails_WhenUnexpectedErrorOccurs() throws Exception {
        when(mealService.searchMeals("pasta")).thenThrow(new IllegalStateException("secret internal detail"));

        mockMvc.perform(get("/api/meals").param("name", "pasta"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.detail").value("An unexpected error occurred"));
    }

    @Test
    void getRandomMeal_ShouldNotBeTreatedAsAnId() throws Exception {
        mockMvc.perform(get("/api/meals/random"))
                .andExpect(status().isOk());

        verify(mealService).getRandomMeal();
    }
}
