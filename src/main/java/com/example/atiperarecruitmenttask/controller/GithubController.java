package com.example.atiperarecruitmenttask.controller;

import com.example.atiperarecruitmenttask.model.dto.GithubRepositoryDto;
import com.example.atiperarecruitmenttask.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GithubController {
    private final GithubService githubService;

    @GetMapping("/{username}/repos")
    public ResponseEntity<List<GithubRepositoryDto>> getNonForkRepositories(@PathVariable String username) {
        return ResponseEntity.ok(githubService.getNonForkRepositories(username));
    }
}
