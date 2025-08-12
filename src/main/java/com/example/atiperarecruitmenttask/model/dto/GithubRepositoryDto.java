package com.example.atiperarecruitmenttask.model.dto;

import java.util.List;

public record GithubRepositoryDto(
        String repositoryName,
        String ownerLogin,
        List<GithubBranchDto> branches
) {
}
