package com.willen.OrceJa.controllers;

import com.willen.OrceJa.dto.ListAllProjectsDto;
import com.willen.OrceJa.dto.ProjectListDto;
import com.willen.OrceJa.dto.SaveProjectDto;
import com.willen.OrceJa.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveProject(@RequestBody SaveProjectDto projectRequest) {
        projectService.saveProject(projectRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/projectsByClient/{clientId}")
    public ResponseEntity<Set<ProjectListDto>> listProjectsById(@PathVariable("clientId") String clientId) {
        Set<ProjectListDto> projectsResponse = projectService.listProjectByClientId(clientId);

        return new ResponseEntity<>(projectsResponse,HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ListAllProjectsDto>> listAllProjects() {
        List<ListAllProjectsDto> projectsResponse = projectService.listAllProjects();

        return new ResponseEntity<>(projectsResponse,HttpStatus.OK);
    }
}
