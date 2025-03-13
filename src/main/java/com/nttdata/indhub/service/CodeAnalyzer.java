package com.nttdata.indhub.service;

import com.nttdata.indhub.api.GroqApi;
import com.nttdata.indhub.config.ConfigLoader;
import com.nttdata.indhub.exception.CodeAnalysisException;
import com.nttdata.indhub.model.groq.GroqRequest;
import com.nttdata.indhub.model.groq.GroqResponse;
import com.nttdata.indhub.model.groq.MessageRequest;
import feign.Feign;
import feign.gson.GsonDecoder;

import feign.gson.GsonEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CodeAnalyzer {
    private final String apiKey;
    private final String model;
    private GroqApi groqApi;

    // Constructor simplificado con solo apiKey y model
    public CodeAnalyzer() {
        this.apiKey = ConfigLoader.getProperty("apiKey");
        this.model = ConfigLoader.getProperty("model");
        // Usar GsonEncoder y GsonDecoder en lugar de Jackson
        this.groqApi = createDefaultApiClient();
    }

    private GroqApi createDefaultApiClient() {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(GroqApi.class, "https://api.groq.com");
    }

    public String analyzeCode(String oldCode, String newCode) {
        validateCode(oldCode, "Código viejo");
        validateCode(newCode, "Código nuevo");

        String prompt = createPrompt(oldCode, newCode);
        GroqRequest request = createRequest(prompt);

        try {
            GroqResponse response = groqApi.analyze(apiKey, request);
            return processResponse(response);
        } catch (Exception e) {
            log.error("Error during code analysis: {}", e.getMessage(), e);
            throw new CodeAnalysisException("Ocurrió un error en la llamada a Groq.", e);
        }
    }

    private void validateCode(String code, String paramName) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException(paramName + " no puede ser nulo o vacío.");
        }
    }

    private String createPrompt(String oldCode, String newCode) {
        return String.format("""
            Código viejo:
            ```java
            %s
            ```
            Código nuevo:
            ```java
            %s
            ```
            Sugerencias detalladas en español para las diferencias.
            """, oldCode, newCode);
    }

    private GroqRequest createRequest(String prompt) {
        return GroqRequest.builder()
                .model(model)
                .messages(List.of(
                        MessageRequest.builder()
                                .role("user")
                                .content("Eres experto en Java. " + prompt)
                                .build()
                ))
                .temperature(0.7)
                .maxTokens(4096)
                .build();
    }

    private String processResponse(GroqResponse response) {
        if (response == null || response.getChoices().isEmpty()) {
            throw new CodeAnalysisException("La respuesta de la API está vacía.");
        }
        return response.getChoices().getFirst().getMessage().getContent();
    }
}
