package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.SaveBudgetDto;
import com.willen.OrceJa.dto.SaveItemDto;
import com.willen.OrceJa.entities.Budget;
import com.willen.OrceJa.entities.Project;
import com.willen.OrceJa.enums.BudgetStatus;
import com.willen.OrceJa.enums.ProjectStatus;
import com.willen.OrceJa.exceptions.ObjectNotFoundException;
import com.willen.OrceJa.repositories.BudgetRepository;
import com.willen.OrceJa.repositories.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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

        @Test
        @DisplayName("Deve lançar excessão ao cadastrar orçamento de projeto inexistente")
        void shouldBeThrowWhenRegisterBudgetWithUnregisteredProject() {
            UUID projectId = UUID.randomUUID();
            doThrow(ObjectNotFoundException.class).when(projectRepository).findById(uuidCaptor.capture());

            List<SaveItemDto> items = List.of(
                    new SaveItemDto("item title I", 2, BigDecimal.valueOf(3.50)),
                    new SaveItemDto("item title II", 5, BigDecimal.valueOf(2.99))
            );

            SaveBudgetDto budgetRequest = new SaveBudgetDto(
                    projectId,
                    "Project details",
                    items,
                    BudgetStatus.IN_PROGRESS
            );

            Assertions.assertThrows(ObjectNotFoundException.class, () -> budgetService.saveBudget(budgetRequest));
            assertEquals(uuidCaptor.getValue(), projectId);
            verify(budgetRepository, times(0)).save(any());
        }
    }

    @Nested
    class findBudget {

        @Test
        @DisplayName("Deve buscar um orçamento cadastrado por id")
        void shouldBeAbleFindBudgetById() {
            UUID budgetId = UUID.randomUUID();
            Budget budget = new Budget(
                    budgetId,
                    BigDecimal.valueOf(23.55),
                    "Budget details",
                    BudgetStatus.IN_PROGRESS);


            doReturn(Optional.of(budget)).when(budgetRepository).findById(uuidCaptor.capture());

            Budget output = budgetService.findBudgetById(budgetId);

            assertNotNull(output);
            assertEquals(budgetId, uuidCaptor.getValue());
        }

        @Test
        @DisplayName("Deve lançar excessão ao buscar budget inexistente")
        void shouldBeThrowWhenFindUnregisteredBudget() {
            UUID budgetId = UUID.randomUUID();
            doThrow(ObjectNotFoundException.class).when(budgetRepository).findById(uuidCaptor.capture());

            assertThrows(ObjectNotFoundException.class, () -> budgetService.findBudgetById(budgetId));
            assertEquals(uuidCaptor.getValue(), budgetId);
        }
    }
}