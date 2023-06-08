package edu.titles.api.dto.director;

import edu.titles.model.Director;
import lombok.Value;

import java.time.LocalDate;

@Value
public
class DirectorBaseDto {

    String name;

    String surname;

    LocalDate birthDate;

    public static DirectorBaseDto of(Director director) {
        return new DirectorBaseDto(
                director.getName(),
                director.getSurname(),
                director.getBirthDate()
        );
    }

    public Director to() {
        return new Director(
                null,
                name,
                surname,
                birthDate
        );
    }
}
