package ru.aveskin.jobparser.mapper;

public interface Mapper<D, S> {
    D map(S source);
}