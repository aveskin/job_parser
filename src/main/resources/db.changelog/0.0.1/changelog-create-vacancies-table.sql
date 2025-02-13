--liquibase formatted sql

--changeset aveskin:create-parser-vacancies-table
--comment create table parser.vacancies
create table parser.vacancies
(
    id         serial primary key,
    title      varchar(100) not null,
    company    varchar(100) not null,
    city       varchar(50),
    salary     varchar(255),
    experience varchar(100),
    link       varchar(255) not null,
    created_at timestamp    not null
);
--rollback drop table parser.vacancies;

