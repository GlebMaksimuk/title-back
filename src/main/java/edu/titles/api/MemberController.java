package edu.titles.api;

import edu.titles.api.dto.member.MemberBaseDto;
import edu.titles.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PutMapping("/login")
    public ResponseEntity<Void> login(@RequestBody MemberBaseDto user) {
        if (memberService.isRegistered(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody MemberBaseDto user) {
        if (memberService.register(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
