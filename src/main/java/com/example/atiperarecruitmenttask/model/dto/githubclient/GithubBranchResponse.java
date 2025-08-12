package com.example.atiperarecruitmenttask.model.dto.githubclient;

public record GithubBranchResponse(
        String name,
        Commit commit
) {
    public record Commit(String sha) {}
}
