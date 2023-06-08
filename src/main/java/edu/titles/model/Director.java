package edu.titles.model;

import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@Value
public class Director {
    @With
    Integer directorId;
    String name;
    String surname;
    LocalDate birthDate;
}
