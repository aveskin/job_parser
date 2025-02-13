package ru.aveskin.jobparser.mapper.impl;

import org.springframework.stereotype.Component;
import ru.aveskin.jobparser.dto.VacanciesResponse;
import ru.aveskin.jobparser.mapper.VacanciesResponseToVacanciesMapper;
import ru.aveskin.jobparser.model.VacancyEntity;

import java.util.List;

@Component
public class VacanciesResponseToVacanciesMapperImpl implements VacanciesResponseToVacanciesMapper {
    @Override
    public List<VacanciesResponse> map(List<VacancyEntity> source) {
        return source.stream().map(s -> new VacanciesResponse(s.getTitle(),
                s.getCompany(),
                s.getCity(),
                s.getExperience(),
                s.getSalary(),
                s.getLink(),
                s.getCreatedAt())).toList();
    }
}
