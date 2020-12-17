package br.com.wagnerandrade.springboot.repository;

import br.com.wagnerandrade.springboot.domain.SpringUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringUserRepository extends JpaRepository<SpringUser, Long> {

    SpringUser findByUsername(String username);
}
