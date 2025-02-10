package com.example.service;

import com.example.model.Pattern;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PatternServiceImpl implements PatternService {
    private final Map<String, Pattern> patterns = new ConcurrentHashMap<>();

    public PatternServiceImpl() {
        // Add some sample patterns
        Pattern pattern1 = Pattern.builder()
                .id("1")
                .name("Singleton")
                .type("Creational")
                .useCases("Ensure a class has only one instance")
                .description("The Singleton pattern ensures that a class has only one instance and provides a global point of access to that instance.")
                .category("Design Patterns")
                .phase("Implementation")
                .build();
        
        Pattern pattern2 = Pattern.builder()
                .id("2")
                .name("Observer")
                .type("Behavioral")
                .useCases("Event handling and monitoring")
                .description("Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.")
                .category("Design Patterns")
                .phase("Design")
                .build();
                
        patterns.put(pattern1.getId(), pattern1);
        patterns.put(pattern2.getId(), pattern2);
    }

    @Override
    public Flux<Pattern> getAllPatterns() {
        return Flux.fromIterable(patterns.values());
    }

    @Override
    public Mono<Pattern> getPatternById(String id) {
        return Mono.justOrEmpty(patterns.get(id));
    }

    @Override
    public Flux<Pattern> getPatternsByCategory(String category) {
        return Flux.fromIterable(patterns.values())
                .filter(pattern -> pattern.getCategory().equalsIgnoreCase(category));
    }

    @Override
    public Flux<Pattern> getPatternsByPhase(String phase) {
        return Flux.fromIterable(patterns.values())
                .filter(pattern -> pattern.getPhase().equalsIgnoreCase(phase));
    }

    @Override
    public Mono<Pattern> createPattern(Pattern pattern) {
        patterns.put(pattern.getId(), pattern);
        return Mono.just(pattern);
    }
}
