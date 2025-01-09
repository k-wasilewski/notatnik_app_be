package com.gft;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.List;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
public class NotesControllerIntegrationTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private NotesService notesService;

    @Test
    void getPagesTest() {
        // GIVEN
        List<Note> notes = List.of(new Note("asa", List.of("sdsd", "sss")), new Note("ssss", List.of("sspspsps", "pppwfrf")));
        when(notesService.getAllNotes()).thenReturn(notes);

        // WHEN, THEN
        webTestClient
            .get()
            .uri("/v1/notes")
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(2)
            .jsonPath("$[0].title").isEqualTo("asa")
            .jsonPath("$[0].contents[0]").isEqualTo("sdsd")
            .jsonPath("$[0].contents[1]").isEqualTo("sss");
    }
  }