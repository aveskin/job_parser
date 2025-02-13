package ru.aveskin.jobparser.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.aveskin.jobparser.model.VacancyEntity;
import ru.aveskin.jobparser.service.ParserService;
import ru.aveskin.jobparser.service.VacancyService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobScheduler {
    private final ParserService parserService;
    private final VacancyService vacancyService;

    @Scheduled(fixedRate = 8640000) // запускается раз в час и обновляет БД свежими вакансиями
    public void fetchAndSaveJobs() {
        List<VacancyEntity> vacancyEntities = parserService.parseVacancies("java+junior", 1);
        vacancyService.saveVacancies(vacancyEntities);
    }
}
