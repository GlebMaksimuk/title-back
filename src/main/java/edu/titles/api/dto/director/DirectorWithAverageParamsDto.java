package edu.titles.api.dto.director;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.titles.model.Director;
import edu.titles.model.DirectorWithAverageParams;
import lombok.Value;

@Value
public
class DirectorWithAverageParamsDto {

    @JsonUnwrapped
    Director directorData;

    Double profitAverage;

    Double profitMultiplier;

    public static DirectorWithAverageParamsDto of(DirectorWithAverageParams director) {
        return new DirectorWithAverageParamsDto(
                director.getDirectorData(),
                director.getProfitAverage(),
                director.getProfitMultiplier()
        );
    }

    public DirectorWithAverageParams to() {
        return new DirectorWithAverageParams(
                directorData,
                profitAverage,
                profitMultiplier
        );
    }
}
