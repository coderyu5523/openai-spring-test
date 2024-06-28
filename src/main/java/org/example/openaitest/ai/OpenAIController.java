package org.example.openaitest.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class OpenAIController {

    private final OpenAIService openAIService;

//    @Autowired
//    public OpenAIController(OpenAIService openAIService) {
//        this.openAIService = openAIService;
//    }

    @GetMapping("/")
    public String index() {
        return "index"; // "index"는 "index.mustache" 파일을 의미합니다.
    }

    @PostMapping("/api/openai/generate-poem")
    @ResponseBody
    public String generatePoem(@RequestBody OpenAIRequest.RequestDTO request) throws IOException {
        return openAIService.getResponse(request.getContent());
    }
}