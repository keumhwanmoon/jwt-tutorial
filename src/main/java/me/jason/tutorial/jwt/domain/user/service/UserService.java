package me.jason.tutorial.jwt.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.jason.tutorial.jwt.domain.user.dto.UserCommand;
import me.jason.tutorial.jwt.domain.user.dto.UserOne;
import me.jason.tutorial.jwt.domain.user.entity.Authority;
import me.jason.tutorial.jwt.domain.user.entity.User;
import me.jason.tutorial.jwt.domain.user.exception.AlreadyExistsMemberException;
import me.jason.tutorial.jwt.domain.user.repo.UserRepository;
import me.jason.tutorial.jwt.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserOne signup(UserCommand userCommand) {
        if (userRepository.findOneWithAuthoritiesByUsername(userCommand.getUsername()).orElse(null) != null) {
            throw new AlreadyExistsMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userCommand.getUsername())
                .password(passwordEncoder.encode(userCommand.getPassword()))
                .nickname(userCommand.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserOne.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserOne getUserWithAuthorities(String username) {
        return UserOne.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserOne getMyUserWithAuthorities() {
        return UserOne.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }
}
