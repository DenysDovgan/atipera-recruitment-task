package com.example.atiperarecruitmenttask.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.nio.file.Files;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate rest;

    private static WireMockServer wireMock;

    @BeforeAll
    static void startWireMock() {
        wireMock = new WireMockServer(0);
        wireMock.start();
        configureFor("localhost", wireMock.port());
    }

    @AfterAll
    static void stopWireMock() {
        if (wireMock != null) {
            wireMock.stop();
        }
    }

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        // Redirect GithubClient base URL to WireMock
        registry.add("github.api.base-url", () -> "http://localhost:" + wireMock.port());
    }

    @BeforeEach
    void stubs() throws Exception {
        String repos = Files.readString(new ClassPathResource(
                "wiremock/users_denys_repos.json").getFile().toPath());
        String branches = Files.readString(new ClassPathResource(
                "wiremock/repos_denys_sampleRepo_branches.json").getFile().toPath());

        wireMock.stubFor(get(urlEqualTo("/users/denys/repos"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(repos)));

        wireMock.stubFor(get(urlEqualTo("/repos/denys/sample-repo/branches"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(branches)));
    }

    @Test
    void happyPath_getNonForkUserRepositories() {
        ResponseEntity<String> resp = rest.getForEntity(
                "http://localhost:" + port + "/api/github/denys/repos",
                String.class
        );

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String body = resp.getBody();

        assertThat(body).contains("\"repositoryName\":\"sample-repo\"");
        assertThat(body).doesNotContain("forked-repo");

        assertThat(body).contains("\"name\":\"main\",\"lastCommitSha\":\"aaa111\"");
        assertThat(body).contains("\"name\":\"dev\",\"lastCommitSha\":\"bbb222\"");
    }
}
