package org.richard.backend.person;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.richard.backend.advertisement.Advertisement;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "person", schema = "ticketing_schema")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "phone", nullable = false)
    private Long phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement")
    private Advertisement advertisement;

    @Size(max = 128)
    @NotNull
    @Column(name = "client_status_id", nullable = false, length = 128)
    private String clientStatusId;

}