package ru.aveskin.jobparser.usecase;

import ru.aveskin.jobparser.dto.VacanciesResponse;

import java.util.List;

public interface DailyVacanciesUseCase {
    List<VacanciesResponse> getDailyVacancies();
}
