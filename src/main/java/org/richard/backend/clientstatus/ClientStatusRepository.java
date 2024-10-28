package org.richard.backend.clientstatus;

import org.richard.backend.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientStatusRepository extends JpaRepository<ClientStatus, UUID> {

    List<ClientStatus> findByTitleContainingIgnoreCase(String fragment);

    boolean existsByTitleIgnoreCase(String title);
}