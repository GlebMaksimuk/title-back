package edu.titles.model;

import lombok.Value;
import lombok.With;

@Value
public class Member {
    @With
    Integer memberId;
    String login;
    String password;
}
