package ru.aveskin.jobparser.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aveskin.jobparser.dto.VacanciesResponse;
import ru.aveskin.jobparser.mapper.VacanciesResponseToVacanciesMapper;
import ru.aveskin.jobparser.model.VacancyEntity;
import ru.aveskin.jobparser.service.ParserService;
import ru.aveskin.jobparser.service.VacancyService;
import ru.aveskin.jobparser.usecase.DailyVacanciesUseCase;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DailyVacanciesUseCaseImpl implements DailyVacanciesUseCase {
    private final ParserService parserService;
    private final VacanciesResponseToVacanciesMapper mapper;
    private final VacancyService vacancyService;

    @Override
    public List<VacanciesResponse> getDailyVacancies() {
        List<VacancyEntity> vacancyEntities = parserService.parseVacancies("java+junior", 1);
        vacancyService.saveVacancies(vacancyEntities);
        return mapper.map(vacancyEntities);
    }
}
