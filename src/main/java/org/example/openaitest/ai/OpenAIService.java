package org.example.openaitest.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    public OpenAIService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getResponse(String prompt) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://api.openai.com/v1/chat/completions");
            request.setHeader("Content-Type", "application/json; charset=UTF-8");
            request.setHeader("Authorization", "Bearer " + apiKey);

            String json = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"당신은 음악 스트리밍 관리자입니다. 모든 응답을 한국어로 해주세요.\"}," +
                    "{\"role\": \"user\", \"content\": \"" + prompt + "\"}] }";
            request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                JsonNode jsonNode = objectMapper.readTree(response.getEntity().getContent());
                return jsonNode.get("choices").get(0).get("message").get("content").asText();
            }
        }
    }
}
