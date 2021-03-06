package br.com.wagnerandrade.springboot.repository;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.util.AnimeCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
@Log4j2
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when successful")
    void save_PersistAnime_WhenSuccessfull() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();

        Assertions.assertThat(animeSaved.getId()).isNotNull();

        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when successful")
    void save_UpdatesAnime_WhenSuccessfull() {
        Anime animeToBeSaved = AnimeCreator.createValidUpdatedAnime();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Hajime no Ippo 2");

        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();

        Assertions.assertThat(animeUpdated.getId()).isNotNull();

        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessfull() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name return list of anime when successful")
    void findByName_ReturnListOfAnime_WhenSuccessfull() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeSaved);
    }

    @Test
    @DisplayName("Find By Name return empty list when no anime is found")
    void findByName_ReturnEmptyList_WhenAnimeIsNotFound() {
        List<Anime> animes = this.animeRepository.findByName("Wagner");

        Assertions.assertThat(animes).isEmpty();
    }

    @Test
    @DisplayName("Save thow ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        Anime animeToBeSaved = AnimeCreator.createAnimeNameIsEmpty();

//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(animeToBeSaved))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(animeToBeSaved))
                .withMessageContaining("The anime name cannot be empty");

    }

}