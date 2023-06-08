package edu.titles.model;

import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@Value
public class Actor {
    @With
    Integer actorId;
    String name;
    String surname;
    LocalDate birthDate;
}
