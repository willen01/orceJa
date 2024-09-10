package com.willen.OrceJa.repositories;

import com.willen.OrceJa.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query(value = "select * from tb_project where client_id = :clientId", nativeQuery = true)
    List<Project> listProjectByClientId(UUID clientId) ;
}
