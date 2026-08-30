package com.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.service.AiService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class AiServiceImpl implements AiService {

    @Resource
    ChatModel chatModel;

    @Override
    public SseEmitter chatWithAi(JSONArray context) {
        SseEmitter emitter = new SseEmitter(5000L);

        List<? extends AbstractMessage> list = context.stream().map(item -> {
            JSONObject obj = JSONObject.from(item);
            return switch (obj.getString("type")) {
                case "user" -> new UserMessage(obj.getString("text"));
                case "assistant" -> new AssistantMessage(obj.getString("text"));
                default -> throw new RuntimeException();
            };
        }).toList();
        Prompt prompt =new Prompt(list.toArray(new Message[0]));
        Flux<ChatResponse> flux = chatModel.stream(prompt);
        flux.subscribe(response ->{
            if (response == null) {
                return;
            }
            String text = response.getResult().getOutput().getText();
            if (text == null || text.isEmpty()) {
                return;
            }
            try {
                emitter.send(text);
            }catch (Exception e){
                emitter.completeWithError(e);
            }
        },emitter::completeWithError,emitter::complete);
        return emitter;
    }
}
