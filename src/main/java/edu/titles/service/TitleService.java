package edu.titles.service;

import edu.titles.api.dto.title.TitleWithCastIdsDto;
import edu.titles.api.dto.title.TitleWithDirectorsDto;
import edu.titles.repo.ActorTitleRepo;
import edu.titles.repo.DirectorRepo;
import edu.titles.repo.DirectorTitleRepo;
import edu.titles.repo.TitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class TitleService {

    @Autowired
    private TitleRepo titleRepo;

    @Autowired
    private DirectorRepo directorRepo;

    @Autowired
    private DirectorTitleRepo directorTitleRepo;

    @Autowired
    private ActorTitleRepo actorTitleRepo;

    @Transactional
    public List<TitleWithDirectorsDto> findAllWithDirectors() {
        return titleRepo.findAll().stream().map(t ->
                new TitleWithDirectorsDto(
                        t,
                        directorRepo.findAllForTitle(t.getTitleId())
                )
        ).toList();
    }

    @Transactional
    public void upsert(TitleWithCastIdsDto dto) {
        var title = dto.getTitle();
        var titleNext = titleRepo.upsert(title);
        directorTitleRepo.unbindTitle(title.getTitleId());
        dto.getDirectorIds().forEach(x -> directorTitleRepo.rebindDirectorAndTitle(titleNext.getTitleId(), x));
        actorTitleRepo.unbindTitle(title.getTitleId());
        dto.getDirectorIds().forEach(x -> actorTitleRepo.rebindDirectorAndTitle(titleNext.getTitleId(), x));
    }

    public Optional<TitleWithDirectorsDto> findById(Integer titleId) {
        return titleRepo.findById(titleId).map(t ->
                new TitleWithDirectorsDto(
                        t,
                        directorRepo.findAllForTitle(t.getTitleId())
                )
        );
    }

    public void delete(Integer titleId) {
        titleRepo.deleteById(titleId);
    }
}
