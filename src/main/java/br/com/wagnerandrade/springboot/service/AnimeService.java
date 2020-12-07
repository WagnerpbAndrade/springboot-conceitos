package br.com.wagnerandrade.springboot.service;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.exception.BadRequestException;
import br.com.wagnerandrade.springboot.mapper.AnimeMapper;
import br.com.wagnerandrade.springboot.repository.AnimeRepository;
import br.com.wagnerandrade.springboot.requests.AnimePostDTO;
import br.com.wagnerandrade.springboot.requests.AnimePutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;

    public List<Anime> listAll() {
        return this.repository.findAll();
    }

    public List<Anime> findByName(String name) {
        return this.repository.findByName(name);
    }

    public Anime findByIdOrthrowBadRequestException(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    public Anime save(AnimePostDTO animePostDTO) {
        return this.repository.save(AnimeMapper.INSTANCE.toAnime(animePostDTO));
    }

    public void delete(Long id) {
        this.repository.delete(findByIdOrthrowBadRequestException(id));
    }

    public void update(AnimePutDTO animePutDTO) {
        Anime savedAnime = findByIdOrthrowBadRequestException(animePutDTO.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutDTO);
        anime.setId(savedAnime.getId());
        this.repository.save(anime);
    }
}
