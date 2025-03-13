package com.nttdata.indhub.model.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueComment {
    private Long id;
    private String body;
    private String createdAt;
    private String updatedAt;
    private User user;
}
