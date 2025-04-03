package org.apicrud.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"status", "field1", "stuffs"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 63, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 3, nullable = false)
    private Status status = Status.ON;

    @Column(columnDefinition = "TEXT")
    private String field1;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference  // Adiciona esta anotação para evitar o loop
    private Set<Stuff> stuffs = new HashSet<>();
}