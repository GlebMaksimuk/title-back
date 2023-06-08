package edu.titles.api.dto.title;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.titles.model.Director;
import edu.titles.model.Title;
import lombok.Value;

import java.util.List;

@Value
public class TitleWithDirectorsDto {

    @JsonUnwrapped
    Title title;

    List<Director> directors;
}
