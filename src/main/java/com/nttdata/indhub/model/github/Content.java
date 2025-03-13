package com.nttdata.indhub.model.github;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    @SerializedName("download_url")
    private String downloadUrl;
    private String content;     // Base64-encoded file content

    public Content(String url) {
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getContent() {
        return content;
    }
}
