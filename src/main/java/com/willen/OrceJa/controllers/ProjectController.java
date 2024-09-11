package com.willen.OrceJa.controllers;

import com.willen.OrceJa.dto.ListAllProjectsDto;
import com.willen.OrceJa.dto.ProjectListDto;
import com.willen.OrceJa.dto.SaveProjectDto;
import com.willen.OrceJa.entities.Project;
import com.willen.OrceJa.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(
            summary = "Cadastrar projeto",
            description = "Este endpoint cadastra um novo projeto associado à um cliente já cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }

    )
    @PostMapping("/save")
    public ResponseEntity<Void> saveProject(@RequestBody SaveProjectDto projectRequest) {
        projectService.saveProject(projectRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Buscar projetos",
            description = "Este endpoint realiza a busca dos projetos pertencente à determinado cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "application/json"))
            }

    )
    @GetMapping("/projectsByClient/{clientId}")
    public ResponseEntity<Set<ProjectListDto>> listProjectsById(
            @Parameter(
                    in = ParameterIn.PATH,
                    name = "clientId",
                    required = true,
                    description = "id de cliente já cadastrado",
            schema = @Schema(
                    type = "string",
                    format = "uuid"
            ))
            @PathVariable("clientId") String clientId) {
        Set<ProjectListDto> projectsResponse = projectService.listProjectByClientId(clientId);

        return new ResponseEntity<>(projectsResponse, HttpStatus.OK);
    }


    @GetMapping("/all")
    @Operation(
            summary = "Projetos cadastrados",
            description = "Este endpoint realiza a busca dos todos projetos cadastrados no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json")),
            }

    )
    public ResponseEntity<List<ListAllProjectsDto>> listAllProjects() {
        List<ListAllProjectsDto> projectsResponse = projectService.listAllProjects();

        return new ResponseEntity<>(projectsResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Remover projeto",
            description = "Este endpoint remove um projeto anteriormente cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "404", description = "Project not found")
            }

    )
    @DeleteMapping("/remove/{projectId}")
    public ResponseEntity<Void> removeProject(
            @Parameter(
                    in = ParameterIn.PATH,
                    name = "projectId",
                    required = true,
                    description = "id do projeto",
                    schema = @Schema(
                            type = "string",
                            format = "uuid"
                    ))
            @PathVariable("projectId") String projectId) {
        projectService.deleteProjectById(projectId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
