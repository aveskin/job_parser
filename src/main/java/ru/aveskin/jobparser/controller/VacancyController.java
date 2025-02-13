package ru.aveskin.jobparser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aveskin.jobparser.dto.VacanciesResponse;
import ru.aveskin.jobparser.usecase.DailyVacanciesUseCase;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VacancyController {
    private final DailyVacanciesUseCase dailyVacanciesUseCase;


    @GetMapping("/daily-vacancies")
    @ResponseStatus(HttpStatus.OK)
    public List<VacanciesResponse> getDailyVacancies() {
        return dailyVacanciesUseCase.getDailyVacancies();
    }


}
