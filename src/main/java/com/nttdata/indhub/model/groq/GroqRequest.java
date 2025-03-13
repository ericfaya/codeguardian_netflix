package com.nttdata.indhub.model.groq;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroqRequest {
    private String model;
    private List<MessageRequest> messages;
    private double temperature;

    @SerializedName("max_tokens")
    private int maxTokens;

}