package com.willen.OrceJa.controllers;

import com.willen.OrceJa.dto.SaveBudgetDto;
import com.willen.OrceJa.entities.Budget;
import com.willen.OrceJa.services.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/budgets")
@Tag(name = "Budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Operation(
            summary = "Cadastrar Orçamento",
            description = "Este endpoint cadastra um novo orçamento associado à um projeto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
            }

    )
    @PostMapping("/save")
    public ResponseEntity<Void> saveBudget(@RequestBody SaveBudgetDto request) {
        budgetService.saveBudget(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Busca orçamento",
            description = "Este endpoint busca orçamento cadastrado com base no id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Budget not found")
            }

    )
    @GetMapping("/listOne/{budgetId}")
    public ResponseEntity<Budget> findBudgetById(
            @Parameter(
                    in = ParameterIn.PATH,
                    name = "budgetId",
                    required = true,
                    description = "id de orçamento",
                    schema = @Schema(
                            type = "string",
                            format = "uuid"
                    ))
            @PathVariable("budgetId") String budgetId) {
         Budget budget = budgetService.findBudgetById(UUID.fromString(budgetId));

         return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    @Operation(
            summary = "Deletar orçamento",
            description = "Este endpoint deleta orçamento com base no id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Budget not found")
            }

    )
    @DeleteMapping("/remove/{budgetId}")
    public ResponseEntity<Void> removeBudget(
            @Parameter(
                    in = ParameterIn.PATH,
                    name = "budgetId",
                    required = true,
                    description = "id de orçamento",
                    schema = @Schema(
                            type = "string",
                            format = "uuid"
                    ))
            @PathVariable("budgetId") String budgetId) {
        budgetService.removeBudget(UUID.fromString(budgetId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
