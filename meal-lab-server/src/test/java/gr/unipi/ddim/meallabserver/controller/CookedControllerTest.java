package gr.unipi.ddim.meallabserver.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import gr.unipi.ddim.meallabserver.dto.CookedEntryResponse;
import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.service.CookedService;

@WebMvcTest(CookedController.class)
class CookedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CookedService cookedService;

    @Test
    void recordCookedMeal_ShouldReturn201_WhenRequestIsValid() throws Exception {
        CookedEntryResponse response = new CookedEntryResponse(1L, new MealSummary("1", "Penne", "thumb.jpg"), 5,
                Instant.parse("2026-11-02T10:00:00Z"));
        when(cookedService.recordCookedMeal(any())).thenReturn(response);

        mockMvc.perform(post("/api/cooked")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"mealId": "1", "rating": 5}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void recordCookedMeal_ShouldReturn400_WhenRatingIsOutOfRange() throws Exception {
        mockMvc.perform(post("/api/cooked")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"mealId": "1", "rating": 99}
                        """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(cookedService);
    }

    @Test
    void recordCookedMeal_ShouldReturn400_WhenBodyIsNotJson() throws Exception {
        mockMvc.perform(post("/api/cooked")
                .contentType(MediaType.APPLICATION_JSON)
                .content("not json"))
                .andExpect(status().isBadRequest());
    }
}
