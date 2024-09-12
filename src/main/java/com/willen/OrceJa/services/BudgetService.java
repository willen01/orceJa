package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.SaveBudgetDto;
import com.willen.OrceJa.entities.Budget;
import com.willen.OrceJa.entities.Item;
import com.willen.OrceJa.entities.Project;
import com.willen.OrceJa.exceptions.ObjectNotFoundException;
import com.willen.OrceJa.repositories.BudgetRepository;
import com.willen.OrceJa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class BudgetService {

    private final ProjectRepository projectRepository;

    private final BudgetRepository budgetRepository;

    public BudgetService(ProjectRepository projectRepository, BudgetRepository budgetRepository) {
        this.projectRepository = projectRepository;
        this.budgetRepository = budgetRepository;
    }

    public void saveBudget(SaveBudgetDto budgetRequest) {
        Project project = projectRepository.findById(budgetRequest.projectId())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Project with id " + budgetRequest.projectId() + " not found"));


        Budget budget = new Budget();
        budget.setProject(project);
        budget.setDetails(budgetRequest.details());
        budget.setStatus(budgetRequest.status());

        Set<Item> items = new HashSet<>();

        budgetRequest.items().forEach(item -> {
            Item itemEntity = new Item();
            itemEntity.setBudget(budget);
            itemEntity.setTitle(item.title());
            itemEntity.setQuantity(item.quantity());
            itemEntity.setPrice(item.price());
            itemEntity.setSubtotal(item.price().multiply(BigDecimal.valueOf(item.quantity())));

            items.add(itemEntity);
        });

        budget.setItems(items);
        budget.setTotal(items.stream()
                .map(Item::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        budgetRepository.save(budget);
    }

    public void removeBudget(UUID budgetId) {
        boolean budgetRegistered = budgetRepository.existsById(budgetId);

        if (!budgetRegistered) {
            throw new ObjectNotFoundException("Budget with id " + budgetId + " not found");
        }

        budgetRepository.deleteById(budgetId);
    }

    public Budget findBudgetById(UUID budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ObjectNotFoundException("Budget with id " + budgetId + " not found"));
    }
}
