package org.richard.backend.office;

import org.richard.backend.repository.BasicRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface OfficeRepository extends JpaRepository<Office, UUID> {

    List<Office> findByShortnameContainingIgnoreCase(String fragment);

    boolean existsByShortnameIgnoreCase(String title);

}