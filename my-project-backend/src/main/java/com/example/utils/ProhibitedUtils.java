package com.example.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProhibitedUtils {

    private static final String filePath = "./prohibited.json";
    private static List<String> prohibitedWords;

    @PostConstruct
    public void init(){
        try (FileInputStream stream=new FileInputStream(filePath)){
            JSONArray parse = JSONArray.parse(new String(stream.readAllBytes()));
            prohibitedWords=parse.toJavaList(String.class);
        }catch (IOException e){
            prohibitedWords=new ArrayList<>();
            log.error("违禁词列表读取失败",e);
        }
    }

    public boolean containsProhibitedWord(JSONObject object){
        for (String word:prohibitedWords){
            for (Object op:object.getJSONArray("ops")){
                String text = JSONObject.from(op).getString("insert");
                if (text.contains(word)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsProhibitedWord(String text){
        for (String word:prohibitedWords){
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getProhibitedWords(){
        return prohibitedWords;
    }

    public void setProhibitedWords(List<String> list) {
        prohibitedWords = list;
        try (FileOutputStream stream=new FileOutputStream(filePath)){
            stream.write(JSONArray.toJSONString(list).getBytes());
        }catch (IOException e){
            log.error("更新违禁词文件失败",e);
        }
    }
}
