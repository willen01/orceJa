package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.SaveBudgetDto;
import com.willen.OrceJa.dto.SaveItemDto;
import com.willen.OrceJa.entities.Project;
import com.willen.OrceJa.enums.BudgetStatus;
import com.willen.OrceJa.enums.ProjectStatus;
import com.willen.OrceJa.repositories.BudgetRepository;
import com.willen.OrceJa.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private BudgetService budgetService;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @Nested
    class saveBudget {

        @Test
        @DisplayName("Deve cadastrar um orçamento com sucesso")
        void shoulBeRegisterBudgetWithSuccess() {

            //arrange

            Project project = new Project(
                    UUID.randomUUID(),
                    "Project Name",
                    "Project description",
                    ProjectStatus.COMPLETED,
                    Instant.now());

            List<SaveItemDto> items = List.of(
                    new SaveItemDto("item title I", 2, BigDecimal.valueOf(3.50)),
                    new SaveItemDto("item title II", 5, BigDecimal.valueOf(2.99))
            );

            Mockito.doReturn(Optional.of(project)).when(projectRepository).findById(uuidCaptor.capture());

            //act
            SaveBudgetDto budgetRequest = new SaveBudgetDto(
                    project.getProjectId(),
                    "Project details",
                    items,
                    BudgetStatus.IN_PROGRESS
            );

            budgetService.saveBudget(budgetRequest);

            //assert
            assertEquals(budgetRequest.projectId(), uuidCaptor.getValue());
            verify(projectRepository, times(1)).findById(any());
            verify(budgetRepository, times(1)).save(any());
        }
    }

}