package org.richard.backend.advertising;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "advertising", schema = "ticketing_schema")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertising {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, unique = true, length = 64)
    private String title;

    @Column(name = "description", nullable = false, length = 255)
    private String description;
}