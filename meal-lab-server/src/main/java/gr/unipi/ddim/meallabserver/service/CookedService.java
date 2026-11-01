package gr.unipi.ddim.meallabserver.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.unipi.ddim.meallabserver.dto.CookedEntryResponse;
import gr.unipi.ddim.meallabserver.dto.CookedMealRequest;
import gr.unipi.ddim.meallabserver.entity.CookedEntry;
import gr.unipi.ddim.meallabserver.entity.StoredMeal;
import gr.unipi.ddim.meallabserver.repository.CookedEntryRepository;

@Service
public class CookedService {

    private final CookedEntryRepository cookedEntryRepository;
    private final StoredMealService storedMealService;

    public CookedService(CookedEntryRepository cookedEntryRepository, StoredMealService storedMealService) {
        this.cookedEntryRepository = cookedEntryRepository;
        this.storedMealService = storedMealService;
    }

    @Transactional
    public CookedEntryResponse recordCookedMeal(CookedMealRequest request) {
        StoredMeal meal = storedMealService.findOrFetchMeal(request.mealId());
        CookedEntry entry = cookedEntryRepository.save(new CookedEntry(meal, request.rating()));
        return CookedEntryResponse.from(entry);
    }

    @Transactional(readOnly = true)
    public List<CookedEntryResponse> getCookedEntries() {
        return cookedEntryRepository.findAllByOrderByCookedAtDesc().stream()
                .map(CookedEntryResponse::from)
                .toList();
    }
}
