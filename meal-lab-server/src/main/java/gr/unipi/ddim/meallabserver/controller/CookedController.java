package gr.unipi.ddim.meallabserver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import gr.unipi.ddim.meallabserver.dto.CookedEntryResponse;
import gr.unipi.ddim.meallabserver.dto.CookedMealRequest;
import gr.unipi.ddim.meallabserver.service.CookedService;

@RestController
@RequestMapping("/api/cooked")
public class CookedController {

    private final CookedService cookedService;

    public CookedController(CookedService cookedService) {
        this.cookedService = cookedService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CookedEntryResponse recordCookedMeal(@Valid @RequestBody CookedMealRequest request) {
        return cookedService.recordCookedMeal(request);
    }

    @GetMapping
    public List<CookedEntryResponse> getCookedEntries() {
        return cookedService.getCookedEntries();
    }
}
