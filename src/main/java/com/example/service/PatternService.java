package com.example.service;

import com.example.model.Pattern;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PatternService {
    Flux<Pattern> getAllPatterns();
    Mono<Pattern> getPatternById(String id);
    Flux<Pattern> getPatternsByCategory(String category);
    Flux<Pattern> getPatternsByPhase(String phase);
    Mono<Pattern> createPattern(Pattern pattern);
}
