package org.richard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BasicRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findByTitleContainingIgnoreCase(String fragment);

    boolean existsByTitleIgnoreCase(String title);

}
