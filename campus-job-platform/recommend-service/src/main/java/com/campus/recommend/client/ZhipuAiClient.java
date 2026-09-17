package com.campus.recommend.client;

import com.campus.recommend.config.ZhipuAiProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZhipuAiClient {

    private final ZhipuAiProperties properties;
    private final ObjectMapper objectMapper;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * 调用智谱GLM大模型进行智能推荐
     *
     * @param systemPrompt 系统提示词
     * @param userPrompt   用户消息（包含用户画像+职位列表）
     * @return AI返回的原始内容，失败返回null
     */
    public String chatCompletion(String systemPrompt, String userPrompt) {
        if (!properties.isEnabled()) {
            return null;
        }

        String url = properties.getBaseUrl() + "/chat/completions";

        try {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("model", properties.getModel());
            body.put("temperature", 0.3);
            body.put("max_tokens", 4096);

            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMsg = new LinkedHashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", systemPrompt);
            messages.add(systemMsg);

            Map<String, String> userMsg = new LinkedHashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userPrompt);
            messages.add(userMsg);

            body.put("messages", messages);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + properties.getApiKey());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> result = objectMapper.readValue(
                        response.getBody(), new TypeReference<Map<String, Object>>() {});

                List<Map<String, Object>> choices = (List<Map<String, Object>>) result.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null) {
                        return (String) message.get("content");
                    }
                }
            }

            log.warn("智谱AI返回异常: status={}", response.getStatusCode());
            return null;

        } catch (Exception e) {
            log.error("调用智谱AI失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 解析AI返回的JSON推荐结果
     */
    public List<Map<String, Object>> parseRecommendResults(String aiResponse) {
        if (aiResponse == null || aiResponse.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            String json = aiResponse.trim();

            int start = json.indexOf('[');
            int end = json.lastIndexOf(']');
            if (start >= 0 && end > start) {
                json = json.substring(start, end + 1);
            }

            return objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            log.warn("解析AI推荐结果失败: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
