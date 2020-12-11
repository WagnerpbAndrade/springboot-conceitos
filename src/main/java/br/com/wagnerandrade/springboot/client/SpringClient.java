package br.com.wagnerandrade.springboot.client;

import br.com.wagnerandrade.springboot.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String... args) {
//        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/api/v1/animes/{id}", Anime.class, 2);
//        log.info(entity);
//
//        Anime object = new RestTemplate().getForObject("http://localhost:8080/api/v1/animes/{id}", Anime.class, 2);
//        log.info(object);
//
//        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/api/v1/animes/all", Anime[].class);
//        log.info(Arrays.toString(animes));
//
//        ResponseEntity<List<Anime>> animesList = new RestTemplate().exchange("http://localhost:8080/api/v1/animes/all", HttpMethod.GET, null,
//                new ParameterizedTypeReference<>() {});
//        log.info(animesList.getBody());

//        Anime setePecadosCapitais = Anime.builder().name("sete Pecados Capitais").build();
//        Anime setePecadosCapitaisSaved = new RestTemplate().postForObject("http://localhost:8080/api/v1/animes/", setePecadosCapitais, Anime.class);
//        log.info("Saved anime {}", setePecadosCapitaisSaved);

        Anime samuraiJack = Anime.builder().name("Samurai Jack").build();
        ResponseEntity<Anime> samuraiJackSaved = new RestTemplate().exchange("http://localhost:8080/api/v1/animes/",
                HttpMethod.POST,
                new HttpEntity<>(samuraiJack),
                Anime.class);
        log.info("Saved anime {}", samuraiJackSaved.getBody());
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
