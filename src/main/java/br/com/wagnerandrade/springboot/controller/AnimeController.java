package br.com.wagnerandrade.springboot.controller;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.requests.AnimePostDTO;
import br.com.wagnerandrade.springboot.requests.AnimePutDTO;
import br.com.wagnerandrade.springboot.service.AnimeService;
import br.com.wagnerandrade.springboot.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Page<Anime>> list(Pageable pageable) {
        log.info(this.dateUtil.formaLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(this.animeService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.animeService.findByIdOrthrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(this.animeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostDTO animePostDTO) {
        return new ResponseEntity(this.animeService.save(animePostDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.animeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Anime> update(@RequestBody @Valid AnimePutDTO animePutDTO) {
        this.animeService.update(animePutDTO);
        return ResponseEntity.noContent().build();
    }

}
