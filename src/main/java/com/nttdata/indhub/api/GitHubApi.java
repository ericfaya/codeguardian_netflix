package com.nttdata.indhub.api;

import com.nttdata.indhub.model.github.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface GitHubApi {
    @RequestLine("GET /repos/{owner}/{repo}/pulls/{pull_number}/files")
    @Headers("Authorization: token {token}")
    List<File> getPRFiles(@Param("owner") String owner,
                          @Param("repo") String repo,
                          @Param("pull_number") int pullNumber,
                          @Param("token") String token);

    @RequestLine("GET /repos/{owner}/{repo}/contents/{path}?ref={ref}")
    @Headers("Authorization: token {token}")
    Content getFileContent(@Param("owner") String owner,
                           @Param("repo") String repo,
                           @Param("path") String path,
                           @Param("ref") String ref,
                           @Param("token") String token);

    @RequestLine("GET /repos/{owner}/{repo}/pulls/{pull_number}")
    @Headers("Authorization: token {token}")
    PullRequest getPRDetails(@Param("owner") String owner,
                             @Param("repo") String repo,
                             @Param("pull_number") int pullNumber,
                             @Param("token") String token);

    @RequestLine("POST /repos/{owner}/{repo}/issues/{pull_number}/comments")
    @Headers({
            "Authorization: token {token}",
            "Content-Type: application/vnd.github+json"
    })
    IssueComment createIssueComment(
            @Param("owner") String owner,
            @Param("repo") String repo,
            @Param("pull_number") int pullNumber,
            @Param("token") String token,
            CommentRequest comment);

    @RequestLine("GET /repos/{owner}/{repo}/issues/{pull_number}/comments")
    @Headers("Authorization: token {token}")
    List<IssueComment> getPRComments(
            @Param("owner") String owner,
            @Param("repo") String repo,
            @Param("pull_number") int pullNumber,
            @Param("token") String token);
}
