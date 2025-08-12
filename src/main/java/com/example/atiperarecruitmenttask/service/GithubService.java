package com.example.atiperarecruitmenttask.service;

import com.example.atiperarecruitmenttask.client.GithubClient;
import com.example.atiperarecruitmenttask.model.dto.GithubRepositoryDto;
import com.example.atiperarecruitmenttask.model.dto.githubclient.GithubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient githubClient;

    public List<GithubRepositoryDto> getNonForkRepositories(String username) {
        /*List<GithubRepositoryDto> outputRepos = new ArrayList<>();
        List<GithubRepositoryResponse> fetchedRepos = githubClient.getUserRepos(username);

        for (GithubRepositoryResponse repo : fetchedRepos) {

        }*/

    }
}
