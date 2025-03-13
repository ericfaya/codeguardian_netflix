package com.nttdata.indhub.service;

import com.nttdata.indhub.api.GitHubApi;
import com.nttdata.indhub.model.github.*;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

import java.util.List;
import java.util.Objects;

public class GitHubConnector {

    // URL base absoluta de la API de GitHub
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";

    /**
     * Método que crea e inicializa el cliente Feign con la URL base absoluta.
     *
     * @return una instancia de GitHubApi.
     */
    public static GitHubApi createGitHubAPI() {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(GitHubApi.class, GITHUB_API_BASE_URL);
    }

    /**
     * Obtiene los archivos modificados en un pull request.
     *
     * @param owner      el propietario del repositorio.
     * @param repo       el nombre del repositorio.
     * @param pullNumber el número del pull request.
     * @param token      el token de autenticación.
     * @return una lista de archivos modificados.
     * @throws IllegalArgumentException si algún parámetro es nulo o inválido.
     * @throws RuntimeException         si ocurre un error al comunicarse con la API.
     */
    public List<File> getPRFiles(String owner, String repo, int pullNumber, String token) {
        validateInput(owner, "owner");
        validateInput(repo, "repo");
        validateInput(token, "token");
        validatePositive(pullNumber, "pullNumber");

        GitHubApi gitHubAPI = createGitHubAPI();

        try {
            return gitHubAPI.getPRFiles(owner, repo, pullNumber, token);
        } catch (FeignException e) {
            handleFeignException(e, "Error al obtener archivos del pull request");
            return null; // Nunca llega aquí debido al throw en handleFeignException
        }
    }

    /**
     * Obtiene los detalles de un pull request.
     */
    public PullRequest getPullRequestDetails(String owner, String repo, String token, int pullNumber) {
        validateInput(owner, "owner");
        validateInput(repo, "repo");
        validateInput(token, "token");
        validatePositive(pullNumber, "pullNumber");

        GitHubApi gitHubAPI = createGitHubAPI();
        try {
            return gitHubAPI.getPRDetails(owner, repo, pullNumber, token);
        } catch (FeignException e) {
            handleFeignException(e, "Error al obtener detalles del pull request");
            return null;
        }
    }

    /**
     * Obtiene el contenido de un archivo en un repositorio.
     */
    public Content getFileContent(String owner, String repo, String path, String ref, String token) {
        validateInput(owner, "owner");
        validateInput(repo, "repo");
        validateInput(path, "path");
        validateInput(ref, "ref");
        validateInput(token, "token");

        GitHubApi gitHubAPI = createGitHubAPI();
        try {
            return gitHubAPI.getFileContent(owner, repo, path, ref, token);
        } catch (FeignException e) {
            handleFeignException(e, "Error al obtener contenido del archivo");
            return null;
        }
    }

    /**
     * Crea un comentario en un pull request.
     */
    public IssueComment createPRComment(String owner, String repo, int pullNumber, String token, CommentRequest comment) {
        validateInput(owner, "owner");
        validateInput(repo, "repo");
        validateInput(token, "token");
        validatePositive(pullNumber, "pullNumber");
        Objects.requireNonNull(comment, "comment no puede ser nulo");

        GitHubApi gitHubAPI = createGitHubAPI();
        try {
            return gitHubAPI.createIssueComment(owner, repo, pullNumber, token, comment);
        } catch (FeignException e) {
            handleFeignException(e, "Error al crear comentario en el pull request");
            return null;
        }
    }

    /**
     * Obtiene los comentarios de un pull request.
     */
    public List<IssueComment> getPRComments(String owner, String repo, int pullNumber, String token) {
        validateInput(owner, "owner");
        validateInput(repo, "repo");
        validateInput(token, "token");
        validatePositive(pullNumber, "pullNumber");

        GitHubApi gitHubAPI = createGitHubAPI();
        try {
            return gitHubAPI.getPRComments(owner, repo, pullNumber, token);
        } catch (FeignException e) {
            handleFeignException(e, "Error al obtener comentarios del pull request");
            return null;
        }
    }

    // Métodos auxiliares

    /**
     * Valida que un parámetro String no sea nulo o vacío.
     */
    private void validateInput(String input, String paramName) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(paramName + " no puede ser nulo o vacío");
        }
    }

    /**
     * Valida que un entero sea positivo.
     */
    private void validatePositive(int value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException(paramName + " debe ser mayor a cero");
        }
    }

    /**
     * Maneja excepciones Feign lanzando mensajes más específicos.
     */
    private void handleFeignException(FeignException e, String customMessage) {
        String errorMessage = String.format("%s: %s (status: %d)", customMessage, e.getMessage(), e.status());
        throw new RuntimeException(errorMessage, e);
    }
}
