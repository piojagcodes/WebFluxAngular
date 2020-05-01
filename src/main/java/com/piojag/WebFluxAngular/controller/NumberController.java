package com.piojag.WebFluxAngular.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;


@RestController

public class NumberController {
    @GetMapping("/number")
    public Integer number() {
        return 10;
    }

    @GetMapping("/reactiveNumber")
    public Mono<Integer> reactiveNumber() {
        return Mono.just(20);
    }

    @GetMapping("/numbers")
    public Integer[] numbers() throws InterruptedException {
        Integer[] numbers = new Integer[10];
        for (int i = 0; i < 10; i++) {
            numbers[i] = i + 1;
            Thread.sleep(500);
        }
        return numbers;
    }
    @GetMapping(value = "/reactive-numbers", produces = {"text/event-stream"})
    public Flux<Integer> reativeNumbers() {
        return Flux.create(integerFluxSink -> {
                    for (int i = 1; i <= 10; i++) {
                        integerFluxSink.next(i);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        integerFluxSink.complete();
                    }
                }

        );
    }
}
