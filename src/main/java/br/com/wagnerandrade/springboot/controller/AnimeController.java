package br.com.wagnerandrade.springboot.controller;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/animes")
@Log4j2
@AllArgsConstructor
public class AnimeController {
    private DateUtil dateUtil;

    @GetMapping
    public List<Anime> list() {
        log.info(dateUtil.formaLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return List.of(new Anime("DBZ"), new Anime("Berserk"));
    }

}
