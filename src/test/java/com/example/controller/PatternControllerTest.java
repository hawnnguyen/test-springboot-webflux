package com.example.controller;

import com.example.service.PatternService;
import com.example.service.PatternServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureGraphQlTester
class PatternControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PatternService patternService() {
            return new PatternServiceImpl();
        }
    }

    @Test
    void getAllPatterns_ShouldReturnPatterns() {
        String query = """
            query {
                patterns {
                    id
                    name
                    type
                    category
                    phase
                }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("data.patterns[0].name").entity(String.class).isEqualTo("Singleton")
                .path("data.patterns[0].type").entity(String.class).isEqualTo("Creational");
    }

    @Test
    void getPatternById_ShouldReturnPattern() {
        String query = """
            query {
                patternById(id: "1") {
                    id
                    name
                    type
                }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("data.patternById.id").entity(String.class).isEqualTo("1")
                .path("data.patternById.name").entity(String.class).isEqualTo("Singleton");
    }

    @Test
    void getPatternsByCategory_ShouldReturnPatterns() {
        String query = """
            query {
                patternsByCategory(category: "Design Patterns") {
                    id
                    name
                    category
                }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("data.patternsByCategory[0].name").entity(String.class).isEqualTo("Singleton")
                .path("data.patternsByCategory[0].category").entity(String.class).isEqualTo("Design Patterns");
    }

    @Test
    void createPattern_ShouldCreateAndReturnPattern() {
        String mutation = """
            mutation {
                createPattern(pattern: {
                    id: "3",
                    name: "Factory",
                    type: "Creational",
                    useCases: "Creating objects without exposing creation logic",
                    description: "Define an interface for creating an object",
                    category: "Design Patterns",
                    phase: "Implementation"
                }) {
                    id
                    name
                    type
                }
            }
            """;

        graphQlTester.document(mutation)
                .execute()
                .path("data.createPattern.name").entity(String.class).isEqualTo("Factory")
                .path("data.createPattern.type").entity(String.class).isEqualTo("Creational");
    }
}
