--liquibase formatted sql

--changeset aveskin:create-parser-schema
create schema parser;
--rollback drop schema parser;