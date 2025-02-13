package ru.aveskin.jobparser.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.aveskin.jobparser.exception.ParserException;
import ru.aveskin.jobparser.model.VacancyEntity;
import ru.aveskin.jobparser.service.ParserService;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HtmlParserService implements ParserService {
    @Value("${app.base-url}")
    private String baseUrl;

    public List<VacancyEntity> parseVacancies(String keyword, int duration) {
        String searchPeriod = "&search_period=" + duration + "&items_on_page=100";
        List<VacancyEntity> vacancies = new ArrayList<>();

        try {
            String url = baseUrl + keyword + searchPeriod;
            Document document = Jsoup.connect(url).get();


            Elements vacancyElements = document
                    .select("div[data-qa='vacancy-serp__vacancy vacancy-serp__vacancy_standard'], " +
                            "div[data-qa='vacancy-serp__vacancy vacancy-serp__vacancy_premium'], " +
                            "div[data-qa='vacancy-serp__vacancy vacancy-serp__vacancy_free'], " +
                            "div[data-qa='vacancy-serp__vacancy vacancy-serp__vacancy_standard_plus'] ");


            for (Element vacancyElement : vacancyElements) {

                // Извлекаем название вакансии
                String title = vacancyElement.select("span[data-qa=serp-item__title-text]").text();
                // Ссылка на вакансию
                String link = vacancyElement.select("a[data-qa=serp-item__title]").attr("href");
                // Компания
                Element employerElement = vacancyElement
                        .select("span[data-qa=vacancy-serp__vacancy-employer-text]").first();
                String company = (employerElement != null) ? employerElement.text() : "Не указано";

                //Город
                Element cityElement = vacancyElement
                        .select("span[data-qa=vacancy-serp__vacancy-address]").first();
                String city = (cityElement != null) ? cityElement.text() : "Не указано";

                Document documentVacancy = Jsoup.connect(link).get();

                // Опыт работы
                String experience = documentVacancy.select("span[data-qa='vacancy-experience']").text();

                //ЗП
                Element salaryElement = documentVacancy
                        .select("span.magritte-text___pbpft_3-0-27.magritte-text_style-primary___AQ7MW_3-0-27" +
                                ".magritte-text_typography-label-1-regular___pi3R-_3-0-27").first();
                String salary = (salaryElement != null) ? salaryElement.text() : "Не указано";


                // Вывод информации
                log.info("Название: " + title);
                log.info("Ссылка: " + link);
                log.info("Город: " + city);
                log.info("Компания: " + company);
                log.info("Опыт: " + (experience.isEmpty() ? "Не указан" : experience));
                log.info("Зарплата: " + (salary.isEmpty() ? "Не указана" : salary));
                log.info("---------------");

                VacancyEntity vacancy = new VacancyEntity();
                vacancy.setTitle(title);
                vacancy.setLink(link);
                vacancy.setCity(city);
                vacancy.setExperience(experience);
                vacancy.setCompany(company);
                vacancy.setSalary(salary);
                vacancy.setCreatedAt(Instant.now());
                vacancies.add(vacancy);
            }

        } catch (IOException e) {
            throw new ParserException("Ошибка парсинга: " + e.getMessage());
        }
        return vacancies;
    }
}
