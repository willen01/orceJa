package com.willen.OrceJa.controllers;

import com.willen.OrceJa.dto.SaveBudgetDto;
import com.willen.OrceJa.services.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveBudget(@RequestBody SaveBudgetDto request) {
        budgetService.saveBudget(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{budgetId}")
    public ResponseEntity<Void> removeBudget(@PathVariable("budgetId") String budgetId) {
        budgetService.removeBudget(UUID.fromString(budgetId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
