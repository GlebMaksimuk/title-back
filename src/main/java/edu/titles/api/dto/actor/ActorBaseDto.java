package edu.titles.api.dto.actor;

import edu.titles.model.Actor;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ActorBaseDto {

    String name;
    String surname;
    LocalDate birthDate;

    public static ActorBaseDto of(Actor actor) {
        return new ActorBaseDto(
                actor.getName(),
                actor.getSurname(),
                actor.getBirthDate()
        );
    }

    public Actor to() {
        return new Actor(
                null,
                name,
                surname,
                birthDate
        );
    }
}
