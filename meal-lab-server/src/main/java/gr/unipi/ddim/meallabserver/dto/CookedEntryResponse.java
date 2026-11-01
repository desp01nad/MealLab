package gr.unipi.ddim.meallabserver.dto;

import java.time.Instant;

import gr.unipi.ddim.meallabserver.entity.CookedEntry;

public record CookedEntryResponse(Long id, MealSummary meal, int rating, Instant cookedAt) {

    public static CookedEntryResponse from(CookedEntry entry) {
        return new CookedEntryResponse(entry.getId(), MealSummary.from(entry.getMeal()), entry.getRating(),
                entry.getCookedAt());
    }
}
