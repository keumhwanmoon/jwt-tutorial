package me.jason.tutorial.jwt.domain.user.repo;

import me.jason.tutorial.jwt.domain.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
