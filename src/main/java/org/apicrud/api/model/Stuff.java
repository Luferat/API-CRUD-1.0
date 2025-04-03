package org.apicrud.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(length = 127, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 127)
    private String location;

    @Column(length = 255)
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(length = 3, nullable = false)
    private Status status = Status.ON;

    @Column(columnDefinition = "TEXT")
    private String field1;

    @Column(columnDefinition = "TEXT")
    private String field2;

    @ManyToMany
    @JoinTable(
            name = "stuff_category",
            joinColumns = @JoinColumn(name = "stuff_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference  // Adiciona esta anotação para evitar o loop
    private Set<Category> categories = new HashSet<>();

}