package edu.titles.api.dto.actor;

import edu.titles.model.Actor;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@Value
public class ActorWithIdDto {

    @With
    Integer actorId;
    String name;
    String surname;
    LocalDate birthDate;

    public static ActorWithIdDto of(Actor actor) {
        return new ActorWithIdDto(
                actor.getActorId(),
                actor.getName(),
                actor.getSurname(),
                actor.getBirthDate()
        );
    }

    public Actor to() {
        return new Actor(
                actorId,
                name,
                surname,
                birthDate
        );
    }
}
