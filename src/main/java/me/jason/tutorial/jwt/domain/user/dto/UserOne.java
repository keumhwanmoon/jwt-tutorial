package me.jason.tutorial.jwt.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.jason.tutorial.jwt.domain.user.entity.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOne {
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    private Set<AuthorityOne> authorityOneSet;

    public static UserOne from(User user) {
        if(user == null) return null;

        return UserOne.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityOneSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityOne.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
