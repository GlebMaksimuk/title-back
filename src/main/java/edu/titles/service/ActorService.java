package edu.titles.service;

import edu.titles.api.dto.actor.ActorWithIdDto;
import edu.titles.model.Actor;
import edu.titles.repo.ActorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ActorService {

    @Autowired
    private ActorRepo actorRepo;

    public List<ActorWithIdDto> findAll() {
        return actorRepo.findAll().stream()
                .map(ActorWithIdDto::of)
                .toList();
    }

    public Optional<ActorWithIdDto> findById(Integer id) {
        return actorRepo.findById(id).map(ActorWithIdDto::of);
    }

    public void upsert(Actor directorDtoBase) {
        actorRepo.upsert(directorDtoBase);
    }

    public void delete(Integer actorId) {
        actorRepo.deleteById(actorId);
    }
}
