package org.richard.backend.person;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.proxy.HibernateProxy;
import org.richard.backend.advertisement.Advertisement;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "person", schema = "ticketing_schema")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "phone_primary", nullable = false, length = 15)
    private String phonePrimary;

    @Column(name = "phone_secondary", length = 15)
    private String phoneSecondary;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telegram", length = 200)
    private String telegram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement")
    @ToString.Exclude
    private Advertisement advertisement;

    @Column(name = "client_status", nullable = false, length = 128)
    private String clientStatus;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Person person = (Person) o;
        return getId() != null && Objects.equals(getId(), person.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}