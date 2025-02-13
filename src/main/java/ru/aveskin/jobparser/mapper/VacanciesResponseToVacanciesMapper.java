package ru.aveskin.jobparser.mapper;

import ru.aveskin.jobparser.dto.VacanciesResponse;
import ru.aveskin.jobparser.model.VacancyEntity;

import java.util.List;

public interface VacanciesResponseToVacanciesMapper extends Mapper<List<VacanciesResponse>, List<VacancyEntity>>{
}
