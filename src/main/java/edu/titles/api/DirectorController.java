package edu.titles.api;

import edu.titles.api.dto.director.DirectorBaseDto;
import edu.titles.api.dto.director.DirectorWithAverageParamsDto;
import edu.titles.api.dto.director.DirectorWithIdDto;
import edu.titles.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping("/directors")
    public List<DirectorWithAverageParamsDto> findAllCalculatingAverageProfit() {
        return directorService.findAllCalculatingProfit();
    }

    @GetMapping("/directors/{id}")
    public ResponseEntity<DirectorWithIdDto> findById(@PathVariable Integer id) {
        var director = directorService.findById(id);
        return director
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/directors")
    public ResponseEntity<Void> create(@RequestBody DirectorBaseDto directorDtoBase) {
        directorService.upsert(directorDtoBase.to());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/directors")
    public ResponseEntity<Void> update(@RequestBody DirectorWithIdDto directorDtoBase) {
        directorService.upsert(directorDtoBase.to());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/directors/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        directorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
