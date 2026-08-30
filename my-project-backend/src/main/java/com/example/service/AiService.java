package com.example.service;

import com.alibaba.fastjson2.JSONArray;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AiService {
    SseEmitter chatWithAi(JSONArray content);
}
