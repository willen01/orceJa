package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.ListAllProjectsDto;
import com.willen.OrceJa.dto.ProjectListDto;
import com.willen.OrceJa.dto.SaveProjectDto;
import com.willen.OrceJa.entities.Client;
import com.willen.OrceJa.entities.Project;
import com.willen.OrceJa.enums.ProjectStatus;
import com.willen.OrceJa.exceptions.ObjectNotFoundException;
import com.willen.OrceJa.repositories.ClientRepository;
import com.willen.OrceJa.repositories.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ProjectService projectService;

    @Captor
    private ArgumentCaptor<Project> projectCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @Nested
    class registerProject {

        @Test()
        @DisplayName("Deve ser possivel cadastrar um projeto com sucesso")
        void shouldBeAbleRegisterProjectWithSuccess() {

            // arrange
            UUID clientId = UUID.randomUUID();
            Client client = new Client(clientId, "Jhon Doe", "jhon.doe@provider.com", "95953256985", Instant.now());
            doReturn(Optional.of(client)).when(clientRepository).findById(any());

            UUID projectId = UUID.randomUUID();
            Project project = new Project(projectId, "Project name", "Project description", ProjectStatus.IN_PROGRESS, Instant.now());
            project.setClient(client);

            doReturn(project).when(projectRepository).save(projectCaptor.capture());

            SaveProjectDto inputRequest = new SaveProjectDto(clientId.toString(), "Project name", "Project description", ProjectStatus.IN_PROGRESS);

            //act
            projectService.saveProject(inputRequest);

            //assert
            Project valueCaptured = projectCaptor.getValue();

            assertEquals(inputRequest.name(), project.getName());
            assertEquals(inputRequest.description(), project.getDescription());
            assertEquals(inputRequest.status(), project.getStatus());
            assertEquals(inputRequest.clientId(), project.getClient().getClientId().toString());
        }

        @Test
        @DisplayName("Não deve ser possível cadastrar projeto para cliente não cadastrado")
        void shouldNotBeAbleRegisterProjectToUnregisteredClient() {
            //Arrage
            doReturn(Optional.empty()).when(clientRepository).findById(any());

            UUID unregisteredUserId = UUID.randomUUID();
            SaveProjectDto inputRequest = new SaveProjectDto(unregisteredUserId.toString(), "Project name", "Project description", ProjectStatus.IN_PROGRESS);

            //act and assert
            assertThrows(ObjectNotFoundException.class, () -> projectService.saveProject(inputRequest));
            verify(projectRepository, times(0)).save(any());
        }
    }

    @Nested
    class listProject {

        @Test
        @DisplayName("Deve ser possivel listar os projetos cadastrados por id do usuário")
        void shouldBeAbleListProjectsByClientId() {
            //arrange
            UUID clientId = UUID.randomUUID();
            doReturn(true).when(clientRepository).existsById(clientId);

            List<Project> projects = List.of(
                    new Project(UUID.randomUUID(), "project name", "project description", ProjectStatus.COMPLETED, Instant.now()),
                    new Project(UUID.randomUUID(), "project name II", "project descriptionII", ProjectStatus.IN_PROGRESS, Instant.now())
            );

            doReturn(projects).when(projectRepository).listProjectByClientId(clientId);

            //act
            Set<ProjectListDto> output = projectService.listProjectByClientId(clientId.toString());

            assertNotNull(output);
            assertEquals(output.size(), projects.size());
            verify(clientRepository, times(1)).existsById(clientId);
            verify(projectRepository, times(1)).listProjectByClientId(clientId);
        }

        @Test
        @DisplayName("Não deve listar projetos para cliente não cadastrado")
        void shouldNotBeAbleListProjectsToUnregisteredClient() {
            // arrange
            UUID clientId = UUID.randomUUID();
            doReturn(false).when(clientRepository).existsById(any());

            // act and assert;
            assertThrows(ObjectNotFoundException.class, () -> projectService.listProjectByClientId(clientId.toString()));
            verify(projectRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("Deve listar todos os projetos cadastrados")
        void shouldBeAbleListAllProjects() {
            Client client = new Client(UUID.randomUUID(), "Jhon Doe", "jhon.doe@provider.com", "95953256985", Instant.now());

            Project projectI = new Project(UUID.randomUUID(), "project name", "project description", ProjectStatus.COMPLETED, Instant.now());
            projectI.setClient(client);

            Project projectII = new Project(UUID.randomUUID(), "project name II", "project descriptionII", ProjectStatus.IN_PROGRESS, Instant.now());
            projectII.setClient(client);

            List<Project> projects = List.of(projectI, projectII);

            doReturn(projects).when(projectRepository).findAll();

            List<ListAllProjectsDto> output = projectService.listAllProjects();

            assertNotNull(output);
            assertEquals(output.size(), projects.size());
            verify(projectRepository, times(1)).findAll();
        }
    }

    @Nested
    class deleteProject {

        @Test
        @DisplayName("Deve remover um projeto cadastrado com sucesso")
        void shouldBeAbleRemoveProjectWithSuccess() {
            UUID projectId = UUID.randomUUID();
            doReturn(true).when(projectRepository).existsById(uuidCaptor.capture());

            projectService.deleteProjectById(projectId.toString());

            verify(projectRepository, times(1)).deleteById(uuidCaptor.capture());
            assertEquals(projectId, uuidCaptor.getValue());
        }

        @Test
        @DisplayName("Não deve ser possivel remover projeto inexistente")
        void shouldNotBeAbleRemoveUnregisteredProject(){
            UUID projectId = UUID.randomUUID();
            doReturn(false).when(projectRepository).existsById(any());

            assertThrows(ObjectNotFoundException.class, () -> projectService.deleteProjectById(projectId.toString()));
            verify(projectRepository, times(0)).deleteById(any());
        }
    }
}