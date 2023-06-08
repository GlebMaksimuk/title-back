package edu.titles.api.dto.director;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.titles.model.Director;
import lombok.Value;

@Value
public
class DirectorWithIdDto {

    @JsonUnwrapped
    DirectorBaseDto base;

    Integer directorId;

    public static DirectorWithIdDto of(Director director) {
        return new DirectorWithIdDto(
                DirectorBaseDto.of(director),
                director.getDirectorId()
        );
    }

    public Director to() {
        return base.to().withDirectorId(directorId);
    }
}
