package edu.titles.api.dto.title;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.titles.model.Title;
import lombok.Value;

import java.util.List;

@Value
public class TitleWithCastIdsDto {

    @JsonUnwrapped
    Title title;

    List<Integer> directorIds;

    List<Integer> actorIds;
}
