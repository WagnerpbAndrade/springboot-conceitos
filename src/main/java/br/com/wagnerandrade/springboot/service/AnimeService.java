package br.com.wagnerandrade.springboot.service;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.exception.BadRequestException;
import br.com.wagnerandrade.springboot.mapper.AnimeMapper;
import br.com.wagnerandrade.springboot.repository.AnimeRepository;
import br.com.wagnerandrade.springboot.requests.AnimePostRequestBody;
import br.com.wagnerandrade.springboot.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;

    public Page<Anime> listAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public List<Anime> listAllNonPageable() {
        return this.repository.findAll();
    }

    public List<Anime> findByName(String name) {
        return this.repository.findByName(name);
    }

    public Anime findByIdOrthrowBadRequestException(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return this.repository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(Long id) {
        this.repository.delete(findByIdOrthrowBadRequestException(id));
    }

    public void update(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrthrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        this.repository.save(anime);
    }
}
