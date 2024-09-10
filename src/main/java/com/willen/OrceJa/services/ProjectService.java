package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.SaveProjectDto;
import com.willen.OrceJa.entities.Client;
import com.willen.OrceJa.entities.Project;
import com.willen.OrceJa.repositories.ClientRepository;
import com.willen.OrceJa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

        if (projectRequest.status() == null) {
            project.setStatus(projectRequest.status());
        }

        projectRepository.save(project);
    }

}
