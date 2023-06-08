package edu.titles.service;

import edu.titles.api.dto.director.DirectorWithAverageParamsDto;
import edu.titles.api.dto.director.DirectorWithIdDto;
import edu.titles.model.Director;
import edu.titles.repo.DirectorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DirectorService {

    @Autowired
    private DirectorRepo directorRepo;

    public List<DirectorWithAverageParamsDto> findAllCalculatingProfit() {
        return directorRepo.findDirectorCalculatingAverageParams()
                .stream()
                .map(DirectorWithAverageParamsDto::of)
                .collect(Collectors.toList());
    }

    public Optional<DirectorWithIdDto> findById(Integer id) {
        return directorRepo.findById(id).map(DirectorWithIdDto::of);
    }

    public void upsert(Director director) {
        directorRepo.upsert(director);
    }

    public void delete(Integer directorId) {
        directorRepo.deleteById(directorId);
    }
}
