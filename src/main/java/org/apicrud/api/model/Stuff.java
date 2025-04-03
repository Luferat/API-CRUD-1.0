package org.apicrud.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stuff")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"status", "field1", "field2"})
public class Stuff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(length = 3, nullable = false)
    private Status status = Status.ON;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @ManyToMany
    @JoinTable(
            name = "stuff_category",
            joinColumns = @JoinColumn(name = "stuff_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
}
