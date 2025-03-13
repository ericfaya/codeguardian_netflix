package com.nttdata.indhub.api;

import com.nttdata.indhub.model.groq.GroqRequest;
import com.nttdata.indhub.model.groq.GroqResponse;
import feign.Headers;
import feign.RequestLine;

@Headers("Content-Type: application/json")
public interface GroqApi {
    @RequestLine("POST /openai/v1/chat/completions")
    @Headers("Authorization: Bearer {apiKey}")
    GroqResponse analyze(@feign.Param("apiKey") String apiKey, GroqRequest request);
}