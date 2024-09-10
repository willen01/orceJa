package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.ProjectListDto;
import com.willen.OrceJa.dto.SaveProjectDto;
import com.willen.OrceJa.entities.Client;
import com.willen.OrceJa.entities.Project;
import com.willen.OrceJa.exceptions.ObjectNotFoundException;
import com.willen.OrceJa.repositories.ClientRepository;
import com.willen.OrceJa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ClientRepository clientRepository;

    public ProjectService(ProjectRepository projectRepository, ClientRepository clientRepository) {
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
    }

    public void saveProject(SaveProjectDto projectRequest) {
        Client client = clientRepository.findById(UUID.fromString(projectRequest.clientId())).orElseThrow();

        Project project = new Project();

        project.setClient(client);
        project.setName(projectRequest.name());
        project.setDescription(projectRequest.description());
        project.setStatus(projectRequest.status());

        projectRepository.save(project);
    }

    public Set<ProjectListDto> listProjectByClientId(String clientId) {
        boolean existsClient = clientRepository.existsById(UUID.fromString(clientId));

        if (!existsClient)  {
            throw new ObjectNotFoundException("User with id " + clientId + " not found");
        }

        List<Project> projects = projectRepository.listProjectByClientId(UUID.fromString(clientId));
//        List<ProjectListDto> projectResponse = new ArrayList<>();

        return projects.stream()
                .map(p -> new ProjectListDto(p.getProjectId(), p.getName(), p.getDescription(), p.getStatus()))
                .collect(Collectors.toSet());

    }

}
