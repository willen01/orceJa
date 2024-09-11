package com.willen.OrceJa.repositories;

import com.willen.OrceJa.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public  interface BudgetRepository extends JpaRepository<Budget, UUID> {
}
