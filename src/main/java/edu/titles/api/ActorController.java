package edu.titles.api;

import edu.titles.api.dto.actor.ActorBaseDto;
import edu.titles.api.dto.actor.ActorWithIdDto;
import edu.titles.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/actors")
    public List<ActorWithIdDto> findAll() {
        return actorService.findAll();
    }

    @GetMapping("/actors/{id}")
    public ResponseEntity<ActorWithIdDto> findById(@PathVariable Integer id) {
        var director = actorService.findById(id);
        return director
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/actors")
    public ResponseEntity<Void> create(@RequestBody ActorBaseDto directorDtoBase) {
        actorService.upsert(directorDtoBase.to());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/actors")
    public ResponseEntity<Void> update(@RequestBody ActorWithIdDto directorDtoBase) {
        actorService.upsert(directorDtoBase.to());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        actorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
