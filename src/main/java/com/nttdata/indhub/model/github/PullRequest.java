package com.nttdata.indhub.model.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PullRequest {
    private String id;
    private Head head;
    private Base base;
}
