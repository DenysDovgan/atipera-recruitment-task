package com.example.atiperarecruitmenttask.controller;

import com.example.atiperarecruitmenttask.model.dto.GithubRepositoryDto;
import com.example.atiperarecruitmenttask.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GithubController {
    private final GithubService githubService;

    @GetMapping("/{username}/repos")
    public ResponseEntity<List<GithubRepositoryDto>> getNonForkUserRepositories(@PathVariable String username) {
        return ResponseEntity.ok(githubService.getNonForkRepositories(username));
    }
}
