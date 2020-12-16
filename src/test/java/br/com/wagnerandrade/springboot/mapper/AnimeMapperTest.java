package br.com.wagnerandrade.springboot.mapper;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.requests.AnimePostRequestBody;
import br.com.wagnerandrade.springboot.util.AnimePostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeMapperTest {

    @Test
    void shouldMapAnimetoRequest() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        String expectedName = animePostRequestBody.getName();

        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);

        Assertions.assertThat( anime ).isNotNull();
        Assertions.assertThat( anime.getName() ).isEqualTo( expectedName );

    }

}