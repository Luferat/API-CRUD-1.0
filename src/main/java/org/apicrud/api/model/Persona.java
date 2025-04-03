package org.apicrud.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "persona")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties("stuffs")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date = LocalDateTime.now();

    private String photo;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 15)
    private String tel;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    private LocalDate birth;

    @Column(length = 14, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(length = 8, nullable = false)
    private Type type = Type.ADMIN;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @Enumerated(EnumType.STRING)
    @Column(length = 3, nullable = false)
    private Status status = Status.ON;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stuff> stuffs;
}