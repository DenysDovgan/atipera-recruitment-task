package com.example.atiperarecruitmenttask.client;

import com.example.atiperarecruitmenttask.exception.GithubClientException;
import com.example.atiperarecruitmenttask.exception.GithubUserNotFoundException;
import com.example.atiperarecruitmenttask.model.dto.githubclient.GithubBranchResponse;
import com.example.atiperarecruitmenttask.model.dto.githubclient.GithubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GithubClient {
    private final RestClient restClient;

    /**
     * Calls: GET /users/{username}/repos
     * Returns all repos (caller will filter fork==true).
     * If user not found - throws GithubUserNotFoundException.
     */
    public List<GithubRepositoryResponse> getUserRepos(String username) {
        log.debug("Fetching repositories for user: {}", username);
        try {
            return restClient.get()
                    .uri(uri -> uri.path("/users/{username}/repos").build(username))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        if (res.getStatusCode().value() == 404) {
                            log.warn("GitHub user not found: {}", username);
                            throw new GithubUserNotFoundException(username);
                        }
                    })
                    .body(new ParameterizedTypeReference<>() {});
        } catch (GithubUserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching repos for user: {}", username, e);
            throw new GithubClientException("Failed to fetch user repos from GitHub", e);
        }
    }

    /**
     * Calls: GET /repos/{owner}/{repo}/branches
     * Returns branches with commit hash.
     */
    public List<GithubBranchResponse> getRepoBranches(String owner, String repo) {
        log.debug("Fetching branches for repo: {}/{}", owner, repo);
        try {
            return restClient.get()
                    .uri(uri -> uri.path("/repos/{owner}/{repo}/branches").build(owner, repo))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (Exception e) {
            log.error("Error fetching branches for repo: {}/{}", owner, repo, e);
            throw new GithubClientException("Failed to fetch branches from GitHub for " + owner + "/" + repo, e);
        }
    }
}
