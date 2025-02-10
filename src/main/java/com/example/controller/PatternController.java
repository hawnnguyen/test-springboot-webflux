package com.example.controller;

import com.example.model.Pattern;
import com.example.service.PatternService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PatternController {
    private final PatternService patternService;

    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }

    @QueryMapping
    public Flux<Pattern> patterns() {
        return patternService.getAllPatterns();
    }

    @QueryMapping
    public Mono<Pattern> patternById(@Argument String id) {
        return patternService.getPatternById(id);
    }

    @QueryMapping
    public Flux<Pattern> patternsByCategory(@Argument String category) {
        return patternService.getPatternsByCategory(category);
    }

    @MutationMapping
    public Mono<Pattern> createPattern(@Argument Pattern pattern) {
        return patternService.createPattern(pattern);
    }
}
