package br.com.wagnerandrade.springboot.repository;

import br.com.wagnerandrade.springboot.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findByName(String name);
}
