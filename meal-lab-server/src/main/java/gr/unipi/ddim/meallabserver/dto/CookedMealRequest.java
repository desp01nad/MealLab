package gr.unipi.ddim.meallabserver.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CookedMealRequest(
        @NotNull @Pattern(regexp = "\\d+") String mealId,
        @NotNull @Min(1) @Max(5) Integer rating) {
}
