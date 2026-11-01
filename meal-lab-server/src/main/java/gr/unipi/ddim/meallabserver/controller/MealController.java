package gr.unipi.ddim.meallabserver.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import gr.unipi.ddim.meallabserver.dto.MealDetails;
import gr.unipi.ddim.meallabserver.dto.MealSummary;
import gr.unipi.ddim.meallabserver.service.MealService;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<MealSummary> searchMeals(@RequestParam @NotBlank @Size(max = 100) String name) {
        return mealService.searchMeals(name);
    }

    @GetMapping("/{id}")
    public MealDetails getMealById(@PathVariable @Pattern(regexp = "\\d+") String id) {
        return mealService.getMealById(id);
    }

    @GetMapping("/random")
    public MealDetails getRandomMeal() {
        return mealService.getRandomMeal();
    }

}