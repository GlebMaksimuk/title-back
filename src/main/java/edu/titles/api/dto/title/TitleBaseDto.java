package edu.titles.api.dto.title;

import edu.titles.model.Title;
import lombok.Value;

import java.time.LocalDate;

@Value
public
class TitleBaseDto {

    Integer titleId;

    String name;

    Double budget;

    Integer runtime;

    Double boxOffice;

    LocalDate premiereDate;

    public static TitleBaseDto of(Title title) {
        return new TitleBaseDto(
                title.getTitleId(),
                title.getName(),
                title.getBudget(),
                title.getRuntime(),
                title.getBoxOffice(),
                title.getPremiereDate());
    }

    public Title to() {
        return new Title(
                titleId,
                name,
                budget,
                runtime,
                boxOffice,
                premiereDate);
    }
}
