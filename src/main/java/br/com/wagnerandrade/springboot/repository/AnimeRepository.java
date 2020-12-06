package br.com.wagnerandrade.springboot.repository;

import br.com.wagnerandrade.springboot.domain.Anime;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
