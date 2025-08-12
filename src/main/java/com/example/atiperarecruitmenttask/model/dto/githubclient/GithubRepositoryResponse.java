package com.example.atiperarecruitmenttask.model.dto.githubclient;

public record GithubRepositoryResponse(
        String name,
        Owner owner,
        boolean fork
) {
    public record Owner(String login) {}
}
