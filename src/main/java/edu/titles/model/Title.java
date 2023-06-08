package edu.titles.model;

import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@Value
public class Title {
    @With
    Integer titleId;
    String name;
    Double budget;
    Integer runtime;
    Double boxOffice;
    LocalDate premiereDate;
}
