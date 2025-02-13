package ru.aveskin.jobparser.service;

import ru.aveskin.jobparser.model.VacancyEntity;

import java.util.List;

public interface ParserService {
    List<VacancyEntity> parseVacancies(String keyword, int duration);
}
