package com.example.atiperarecruitmenttask.service;

import com.example.atiperarecruitmenttask.client.GithubClient;
import com.example.atiperarecruitmenttask.model.dto.GithubBranchDto;
import com.example.atiperarecruitmenttask.model.dto.GithubRepositoryDto;
import com.example.atiperarecruitmenttask.model.dto.githubclient.GithubBranchResponse;
import com.example.atiperarecruitmenttask.model.dto.githubclient.GithubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient githubClient;

    public List<GithubRepositoryDto> getNonForkRepositories(String username) {
        log.debug("Listing non-fork repositories for user: {}", username);

        List<GithubRepositoryResponse> fetchedRepos = githubClient.getUserRepos(username);

        return fetchedRepos.stream()
                .filter(r -> !r.fork())
                .map(r -> new GithubRepositoryDto(
                        r.name(),
                        r.owner().login(),
                        mapBranches(r.owner().login(), r.name())
                ))
                .toList();
    }

    private List<GithubBranchDto> mapBranches(String owner, String repoName) {
        List<GithubBranchResponse> fetchedBranches = githubClient.getRepoBranches(owner, repoName);

        return fetchedBranches.stream()
                .map(b -> new GithubBranchDto(
                        b.name(),
                        b.commit() != null ? b.commit().sha() : null
                ))
                .toList();
    }
}
