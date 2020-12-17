package br.com.wagnerandrade.springboot.integration;

import br.com.wagnerandrade.springboot.domain.Anime;
import br.com.wagnerandrade.springboot.domain.SpringUser;
import br.com.wagnerandrade.springboot.repository.AnimeRepository;
import br.com.wagnerandrade.springboot.repository.SpringUserRepository;
import br.com.wagnerandrade.springboot.requests.AnimePostRequestBody;
import br.com.wagnerandrade.springboot.util.AnimeCreator;
import br.com.wagnerandrade.springboot.util.AnimePostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateAdmin;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private SpringUserRepository userRepository;

    private static final SpringUser USER = SpringUser.builder()
            .name("Teste")
            .password("{bcrypt}$2a$10$KWI7Jrq8sIeHOKxnVuDSVOSasQnorjqIuMxQjpnG/jyNOlSnIdK46")
            .username("teste")
            .authorities("ROLE_USER")
            .build();

    private static final SpringUser ADMIN = SpringUser.builder()
            .name("Wagner Andrade")
            .password("{bcrypt}$2a$10$KWI7Jrq8sIeHOKxnVuDSVOSasQnorjqIuMxQjpnG/jyNOlSnIdK46")
            .username("wagner")
            .authorities("ROLE_USER,ROLE_ADMIN")
            .build();

    @TestConfiguration
    @Lazy
    static class Config {

        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("teste", "teste");

            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("wagner", "teste");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("findByName returns list of anime when successful")
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        Anime savedAnime = this.animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        this.userRepository.save(USER);

        String expectedName = savedAnime.getName();

        String url = String.format("/api/v1/animes/find?name=%s", expectedName);

        List<Anime> animes = this.testRestTemplateUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        Anime savedAnime = this.animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        this.userRepository.save(USER);

        String expectedName = savedAnime.getName();

        List<Anime> animes = this.testRestTemplateUser.exchange("/api/v1/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Anime savedAnime = this.animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        this.userRepository.save(USER);

        Long expectedId = savedAnime.getId();

        Anime anime = this.testRestTemplateUser.getForObject("/api/v1/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);

    }

    @Test
    @DisplayName("findByName returns a empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        this.userRepository.save(USER);

        List<Anime> animes = this.testRestTemplateUser.exchange("/api/v1/animes/find?name=dbzz", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        this.userRepository.save(USER);

        ResponseEntity<Anime> animeResponseEntity = this.testRestTemplateUser.postForEntity("/api/v1/animes",
                animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();

        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("update updates when successful")
    void update_UpdatesAnime_WhenSuccessful() {
        Anime savedAnime = this.animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        this.userRepository.save(USER);

        savedAnime.setName("new name");

        ResponseEntity<Void> animeResponseEntity = this.testRestTemplateUser.exchange("/api/v1/animes",
                HttpMethod.PUT,
                new HttpEntity<>(savedAnime),
                Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime savedAnime = this.animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        this.userRepository.save(ADMIN);

        ResponseEntity<Void> animeResponseEntity = this.testRestTemplateAdmin.exchange("/api/v1/animes/admin/{id}",
                HttpMethod.DELETE,
                null,
                Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserNotAdmin() {
        Anime savedAnime = this.animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        this.userRepository.save(USER);

        ResponseEntity<Void> animeResponseEntity = this.testRestTemplateUser.exchange("/api/v1/animes/admin/{id}",
                HttpMethod.DELETE,
                null,
                Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }
}
