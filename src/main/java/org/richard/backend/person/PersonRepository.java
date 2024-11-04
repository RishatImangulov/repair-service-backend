package org.richard.backend.person;

import org.richard.backend.repository.BasicRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    List<Person> findByFullNameContainingIgnoreCase(String fragment);

    boolean existsByFullNameIgnoreCase(String fullName);
}