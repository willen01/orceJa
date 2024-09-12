package com.willen.OrceJa.entities;

import com.willen.OrceJa.enums.BudgetStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "budget_id")
    private UUID id;

    private BigDecimal total;

    private String details;

    @Enumerated(EnumType.STRING)
    private BudgetStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Item> items;

    public Budget() {
    }

    public Budget(UUID id, BigDecimal total, String details, BudgetStatus status) {
        this.id = id;
        this.total = total;
        this.details = details;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BudgetStatus getStatus() {
        return status;
    }

    public void setStatus(BudgetStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
