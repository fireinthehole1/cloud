package com.learn.spring.gateway.auth;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.spring.cloud.core.JsonResult;
import feign.form.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;


/**
 * @author bill
 * @version 1.0
 * @date 2020/4/20 21:47
 */
@Component
@Slf4j
@ConfigurationProperties("net.learn.jwt")
public class AuthFilter implements GlobalFilter {
    /**
     * 跳过的路径
     */
    private String[] skipAuthUrls;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("接收请求path:{}",path);
        // WEB登录信息: 控制单点登录
        try {
            if (null!=skipAuthUrls && Arrays.asList(skipAuthUrls).contains(path)){
                return chain.filter(exchange);
            }
            if (path.startsWith("/webapi")) {
                //todo 验证权限
            }
        }catch (Exception e){
            return tokenErro(exchange,JsonResult.failed("认证失败"));
        }
        return chain.filter(exchange);
    }
    private Mono<Void> tokenErro(ServerWebExchange serverWebExchange, JsonResult jsonResult) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(jsonResult);
        } catch (Exception e) {
            jsonStr = JSONObject.toJSONString(jsonResult);
        }
        byte[] bytes = jsonStr.getBytes(CharsetUtil.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(bytes);
        serverWebExchange.getResponse().getHeaders()
                .setContentType(MediaType.APPLICATION_JSON_UTF8);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }
}
