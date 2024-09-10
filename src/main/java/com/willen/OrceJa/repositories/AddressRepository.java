package com.willen.OrceJa.repositories;

import com.willen.OrceJa.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
