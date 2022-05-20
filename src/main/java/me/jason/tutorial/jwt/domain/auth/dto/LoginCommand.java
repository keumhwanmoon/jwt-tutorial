package me.jason.tutorial.jwt.domain.auth.dto;

import lombok.Getter;

@Getter
public class LoginCommand {
    private String username;
    private String password;
}
