package ru.aveskin.jobparser.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(schema = "parser", name = "vacancies")
public class VacancyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 255)
    @NotNull
    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "city")
    private String city;

    @Column(name = "experience")
    private String experience;

    @Column(name = "salary")
    @Size(max = 255)
    private String salary;

    @Size(max = 255)
    @Column(name = "link")
    private String link;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
