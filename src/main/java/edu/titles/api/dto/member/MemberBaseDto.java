package edu.titles.api.dto.member;

import edu.titles.model.Member;
import lombok.Value;

@Value
public
class MemberBaseDto {

    String login;

    String password;

    public static MemberBaseDto of(Member member) {
        return new MemberBaseDto(
                member.getLogin(),
                member.getPassword()
        );
    }

    public Member to() {
        return new Member(null, login, password);
    }
}
