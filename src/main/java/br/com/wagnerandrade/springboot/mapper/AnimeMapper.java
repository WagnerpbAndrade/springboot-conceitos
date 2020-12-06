package br.com.wagnerandrade.springboot.mapper;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.requests.AnimePostDTO;
import br.com.wagnerandrade.springboot.requests.AnimePutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnimeMapper {
    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    Anime toAnime(AnimePostDTO animePostDTO);
    Anime toAnime(AnimePutDTO animePutDTO);
}
