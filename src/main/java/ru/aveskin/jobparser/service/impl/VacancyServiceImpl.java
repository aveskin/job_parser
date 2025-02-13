package ru.aveskin.jobparser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.aveskin.jobparser.model.VacancyEntity;
import ru.aveskin.jobparser.repository.VacancyRepository;
import ru.aveskin.jobparser.service.VacancyService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;

    @Override
    public void saveVacancies(List<VacancyEntity> vacancies) {

        vacancies.stream()
                .filter(vacancy -> vacancyRepository.notExistByCompanyAndTitleAndCreatedAt(
                        vacancy.getCompany(),
                        vacancy.getTitle(),
                        LocalDate.now())
                )
                .forEach(vacancyRepository::save);

        log.info("сохранение вакансий в БД");
    }

    @Override
    public void saveVacancy(VacancyEntity vacancy) {
        vacancyRepository.save(vacancy);
    }
}
