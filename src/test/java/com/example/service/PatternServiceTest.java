package com.example.service;

import com.example.model.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class PatternServiceTest {
    
    private PatternService patternService;
    
    @BeforeEach
    void setUp() {
        patternService = new PatternServiceImpl();
    }
    
    @Test
    void getAllPatterns_ShouldReturnAllPatterns() {
        StepVerifier.create(patternService.getAllPatterns())
                .expectNextCount(2)
                .verifyComplete();
    }
    
    @Test
    void getPatternById_WithValidId_ShouldReturnPattern() {
        StepVerifier.create(patternService.getPatternById("1"))
                .assertNext(pattern -> {
                    assertEquals("Singleton", pattern.getName());
                    assertEquals("Creational", pattern.getType());
                    assertEquals("Design Patterns", pattern.getCategory());
                    assertEquals("Implementation", pattern.getPhase());
                })
                .verifyComplete();
    }
    
    @Test
    void getPatternById_WithInvalidId_ShouldReturnEmpty() {
        StepVerifier.create(patternService.getPatternById("999"))
                .verifyComplete();
    }
    
    @Test
    void getPatternsByCategory_ShouldReturnPatternsInCategory() {
        StepVerifier.create(patternService.getPatternsByCategory("Design Patterns"))
                .expectNextCount(2)
                .verifyComplete();
    }
    
    @Test
    void getPatternsByPhase_ShouldReturnPatternsInPhase() {
        StepVerifier.create(patternService.getPatternsByPhase("Implementation"))
                .assertNext(pattern -> {
                    assertEquals("Singleton", pattern.getName());
                    assertEquals("Implementation", pattern.getPhase());
                })
                .verifyComplete();
    }
    
    @Test
    void createPattern_ShouldAddNewPattern() {
        Pattern newPattern = Pattern.builder()
                .id("3")
                .name("Factory")
                .type("Creational")
                .useCases("Create objects without exposing creation logic")
                .description("Define an interface for creating objects, but let subclasses decide which class to instantiate")
                .category("Design Patterns")
                .phase("Implementation")
                .build();
                
        StepVerifier.create(patternService.createPattern(newPattern))
                .assertNext(pattern -> {
                    assertEquals("Factory", pattern.getName());
                    assertEquals("Creational", pattern.getType());
                })
                .verifyComplete();
                
        // Verify the pattern was actually added
        StepVerifier.create(patternService.getPatternById("3"))
                .assertNext(pattern -> assertEquals("Factory", pattern.getName()))
                .verifyComplete();
    }
}
