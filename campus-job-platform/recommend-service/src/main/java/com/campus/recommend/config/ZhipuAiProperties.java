package com.campus.recommend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "recommend.ai")
public class ZhipuAiProperties {

    private boolean enabled = false;

    private String apiKey;

    private String baseUrl = "https://open.bigmodel.cn/api/paas/v4";

    private String model = "glm-4-flash";

    private int connectTimeout = 5000;

    private int readTimeout = 30000;

    private int maxJobs = 30;

    private int cacheMinutes = 30;
}
