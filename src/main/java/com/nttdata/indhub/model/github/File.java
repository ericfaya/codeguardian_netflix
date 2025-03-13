package com.nttdata.indhub.model.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private String filename;
    private String status;
    private int additions;
    private int deletions;
    private int changes;

    @Override
    public String toString() {
        return "File: " + filename + " (" + status + ")";
    }
}
