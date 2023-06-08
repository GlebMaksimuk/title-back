package edu.titles.service;

import edu.titles.api.dto.member.MemberBaseDto;
import edu.titles.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberService {

    @Autowired
    private MemberRepo memberRepo;

    public Boolean isRegistered(MemberBaseDto user) {
        var userOpt = memberRepo.findByLogin(user.getLogin());
        return userOpt.filter(value -> user.getPassword().equals(value.getPassword())).isPresent();
    }

    public Boolean register(MemberBaseDto user) {
        if (isRegistered(user) || memberRepo.existsByLogin(user.getLogin())) {
            return false;
        } else {
            memberRepo.save(user.to());
            return true;
        }
    }
}
