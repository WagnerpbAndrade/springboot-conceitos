package br.com.wagnerandrade.springboot.controller;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.requests.AnimePostRequestBody;
import br.com.wagnerandrade.springboot.requests.AnimePutRequestBody;
import br.com.wagnerandrade.springboot.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<Page<Anime>> list(Pageable pageable) {
        return ResponseEntity.ok(this.animeService.listAll(pageable));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll() {
        return ResponseEntity.ok(this.animeService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.animeService.findByIdOrthrowBadRequestException(id));
    }

    @GetMapping(path = "by-id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Anime> findByIdAuthenticationPrincipal(@PathVariable("id") Long id,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(this.animeService.findByIdOrthrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(this.animeService.findByName(name));
    }

    @PostMapping("/admin")
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
        return new ResponseEntity(this.animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/admin/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.animeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin")
    public ResponseEntity<Anime> update(@RequestBody @Valid AnimePutRequestBody animePutRequestBody) {
        this.animeService.update(animePutRequestBody);
        return ResponseEntity.noContent().build();
    }

}
