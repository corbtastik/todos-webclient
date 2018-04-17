package io.corbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
public class TodoClientAPI {

    private WebClient webClient;

    @Autowired
    public TodoClientAPI(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/")
    public Mono<Todo> createTodo(@RequestBody Mono<Todo> todo) {

        Mono<Todo> result = webClient.post()
            .uri("/")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(todo, Todo.class)
            .retrieve().bodyToMono(Todo.class);

        return result;
    }

    @GetMapping("/")
    public Flux<Todo> listTodos() {

        Flux<Todo> result = webClient.get()
                .uri("/")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Todo.class);

        return result;
    }

}
