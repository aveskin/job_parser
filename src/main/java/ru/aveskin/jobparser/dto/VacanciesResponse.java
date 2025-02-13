package ru.aveskin.jobparser.dto;


import java.time.Instant;

public record VacanciesResponse(
        String title,
        String company,
        String city,
        String experience,
        String salary,
        String link,
        Instant createdAt
) {
}
