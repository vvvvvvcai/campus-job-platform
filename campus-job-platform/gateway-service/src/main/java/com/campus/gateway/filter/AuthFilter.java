package com.campus.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 全局鉴权过滤器
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String SECRET = "campus-job-platform-secret-key-must-be-long-enough";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_HEADER = "Authorization";
    private static final Integer ADMIN_ROLE = 2;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 白名单路径（无需鉴权）
     */
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/api/user/login",
            "/api/user/register",
            "/api/user/sms",
            "/api/job/search",
            "/api/job/detail/**",
            "/api/company/detail/**",
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs/**"
    );

    /**
     * 管理员接口路径（需要管理员角色）
     */
    private static final List<String> ADMIN_PATHS = Arrays.asList(
            "/api/user/list",
            "/api/user/status/",
            "/api/user/count",
            "/api/company/list",
            "/api/company/audit/approve/",
            "/api/company/audit/reject/",
            "/api/job/admin/list",
            "/api/job/audit/",
            "/api/job/count"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // 检查是否在白名单中
        if (isWhiteListed(path)) {
            return chain.filter(exchange);
        }

        // 获取Token
        String token = getToken(request);
        if (!StringUtils.hasText(token)) {
            return unauthorizedResponse(exchange, "未登录，请先登录");
        }

        // 验证Token
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return unauthorizedResponse(exchange, "Token解析失败，请重新登录");
            }
            if (claims.getExpiration().before(new java.util.Date())) {
                return unauthorizedResponse(exchange, "Token已过期，请重新登录");
            }

            // 获取用户角色
            Integer role = claims.get("role", Integer.class);

            // 检查是否为管理员接口
            if (isAdminPath(path) && (role == null || !role.equals(ADMIN_ROLE))) {
                return forbiddenResponse(exchange, "无权限访问管理员接口");
            }

            // 将用户信息传递给下游服务
            String userId = String.valueOf(claims.get("userId", Long.class));
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Phone", claims.getSubject())
                    .header("X-User-Role", String.valueOf(role))
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            log.error("Token解析失败: {}", e.getMessage(), e);
            return unauthorizedResponse(exchange, "Token无效，请重新登录");
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private boolean isWhiteListed(String path) {
        return WHITE_LIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private boolean isAdminPath(String path) {
        return ADMIN_PATHS.stream().anyMatch(pattern -> pathMatcher.match(pattern + "**", path));
    }

    private String getToken(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst(TOKEN_HEADER);
        if (StringUtils.hasText(authorization) && authorization.startsWith(TOKEN_PREFIX)) {
            return authorization.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private Claims parseToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"code\":401,\"message\":\"" + message + "\",\"data\":null}";
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    private Mono<Void> forbiddenResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"code\":403,\"message\":\"" + message + "\",\"data\":null}";
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
