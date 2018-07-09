package io.corbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * WebClient uses the same codecs as WebFlux server apps.  It shares base packaging
 * and common APIs with WebFlux.  WebClient features.
 *
 * 1. non-blocking
 * 2. reactive
 * 3. functional API
 * 4. sync and async
 * 5. streaming upload and download to WebFlux enabled server
 */
@RestController
public class TodoClientAPI {

    private WebClient webClient;

    private static final Logger LOG = LoggerFactory.getLogger(TodoClientAPI.class);

    @Autowired
    public TodoClientAPI(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Post WebClient.retrieve() example
     * @param todo
     * @return
     */
    @PostMapping("/")
    public Mono<Todo> createTodo(@RequestBody Mono<Todo> todo) {

        Mono<Todo> mono = webClient.post()
                .uri("/todos/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todo, Todo.class)
                .retrieve()
                .bodyToMono(Todo.class);

        return mono;
    }

    @GetMapping("/")
    public Flux<Todo> retrieve() {

        Flux<Todo> flux = webClient.get()
            .uri("/todos/")
            .retrieve()
            .bodyToFlux(Todo.class);

        return flux;
    }

    /**
     * Get WebClient.exchange() example
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Todo> retrieve(@PathVariable Integer id) {

        Mono<Todo> mono = webClient.get()
            .uri("/todos/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .flatMap(response -> {
                return response.bodyToMono(Todo.class);
            });

        return mono;
    }

    @DeleteMapping("/")
    public void delete() {
        webClient.delete()
            .uri("/todos/")
            .retrieve().bodyToMono(Void.class);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        webClient.delete()
            .uri("/todos/{id}", id)
            .retrieve().bodyToMono(Void.class);
    }

    @PatchMapping("/{id}")
    public Mono<Todo> update(@PathVariable Integer id, @RequestBody Mono<Todo> todo) {
        Mono<Todo> mono = webClient.patch()
            .uri("/todos/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(todo, Todo.class)
            .retrieve()
            .bodyToMono(Todo.class);

        return mono;
    }

}
