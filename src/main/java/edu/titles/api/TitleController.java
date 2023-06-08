package edu.titles.api;

import edu.titles.api.dto.title.TitleWithCastIdsDto;
import edu.titles.api.dto.title.TitleWithDirectorsDto;
import edu.titles.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TitleController {

    @Autowired
    private TitleService service;

    @GetMapping("/titles")
    public List<TitleWithDirectorsDto> findAll() {
        return service.findAllWithDirectors();
    }

    @GetMapping("/titles/{id}")
    public ResponseEntity<TitleWithDirectorsDto> findById(@PathVariable Integer id) {
        var title = service.findById(id);
        return title
                .map(x -> new ResponseEntity<>(x, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/titles")
    public ResponseEntity<Void> update(@RequestBody TitleWithCastIdsDto title) {
        service.upsert(title);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/titles")
    public ResponseEntity<Void> create(@RequestBody TitleWithCastIdsDto title) {
        service.upsert(title);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/titles/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
