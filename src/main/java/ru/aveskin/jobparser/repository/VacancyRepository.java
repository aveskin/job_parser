package ru.aveskin.jobparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aveskin.jobparser.model.VacancyEntity;

import java.time.LocalDate;
import java.util.List;

public interface VacancyRepository extends JpaRepository<VacancyEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN false ELSE true END FROM VacancyEntity v " +
            "WHERE v.company = :company AND v.title = :title AND " +
            "CAST(v.createdAt AS date) = :date")
    boolean notExistByCompanyAndTitleAndCreatedAt(@Param("company") String company,
                                                  @Param("title") String title,
                                                  @Param("date") LocalDate date);

    @Query("SELECT v FROM VacancyEntity v " +
            "WHERE CAST(v.createdAt AS date) = :date")
    List<VacancyEntity> findAllByToday(@Param("date") LocalDate date);
}
