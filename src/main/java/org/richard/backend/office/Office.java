package org.richard.backend.office;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "office", schema = "ticketing_schema")
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 5)
    @NotNull
    @Column(name = "shortname", nullable = false, length = 5)
    private String shortname;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

}