package ru.aveskin.jobparser.service;

import ru.aveskin.jobparser.model.VacancyEntity;

import java.util.List;

public interface VacancyService {
     void saveVacancies(List<VacancyEntity> vacancies);

     void saveVacancy(VacancyEntity vacancy);
}
