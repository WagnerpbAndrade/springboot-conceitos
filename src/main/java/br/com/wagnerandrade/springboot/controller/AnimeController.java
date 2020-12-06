package br.com.wagnerandrade.springboot.controller;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.service.AnimeService;
import br.com.wagnerandrade.springboot.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> list() {
        log.info(this.dateUtil.formaLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(this.animeService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.animeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody Anime anime) {
        return new ResponseEntity(this.animeService.save(anime), HttpStatus.CREATED);
    }

}
