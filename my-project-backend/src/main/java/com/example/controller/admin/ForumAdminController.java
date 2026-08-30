package com.example.controller.admin;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.entity.PageRestBean;
import com.example.entity.RestBean;
import com.example.entity.dto.Topic;
import com.example.entity.vo.request.TopicTypeCreateVO;
import com.example.entity.vo.response.TopicPreviewVO;
import com.example.entity.vo.response.TopicTypeVO;
import com.example.service.TopicService;
import com.example.utils.ProhibitedUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/forum")
public class ForumAdminController {

    @Resource
    TopicService service;

    @Resource
    private ProhibitedUtils prohibitedUtils;

    @GetMapping("/list")
    public PageRestBean<TopicPreviewVO> list(@RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam(required = false) String keyword){
        JSONObject result = service.listAllTopicByPage(page, size,keyword);
        return PageRestBean.success(
                result.getJSONArray("list").toList(TopicPreviewVO.class),
                result.getIntValue("total"),
                page);
    }

    @GetMapping("/delete")
    public RestBean<Void> deleteTopic(@RequestParam int tid){
        service.deleteTopic(tid);
        return RestBean.success();
    }

    @PostMapping("/top")
    public RestBean<Topic> setTop(@RequestBody JSONObject object){
        service.setTopicTop(
                object.getIntValue("tid"),
                object.getBooleanValue("status")
        );
        return RestBean.success();
    }

    @PostMapping("/locked")
    public RestBean<Topic> setLocked(@RequestBody JSONObject object){
        service.setTopicLocked(
                object.getIntValue("tid"),
                object.getBooleanValue("locked")
        );
        return RestBean.success();
    }

    @PostMapping("/invisible")
    public RestBean<Topic> setInvisible(@RequestBody JSONObject object){
        service.setTopicInvisible(
                object.getIntValue("tid"),
                object.getBooleanValue("status")
        );
        return RestBean.success();
    }

    @GetMapping("/prohibited-list")
    public RestBean<List<String>> getProhibitedWords(){
        return RestBean.success(prohibitedUtils.getProhibitedWords());
    }

    @PostMapping("/prohibited-save")
    public RestBean<Void> saveProhibitedWords(@RequestBody JSONArray array){
        prohibitedUtils.setProhibitedWords(array.toList(String.class));
        return RestBean.success();
    }

    @PostMapping("/update-type")
    public RestBean<Void> updateType(@RequestBody TopicTypeVO vo){
        service.updateTopicType(vo);
        return RestBean.success();
    }

    @GetMapping("delete-type")
    public RestBean<Void> deleteType(@RequestParam int tid){
        service.deleteTopicType(tid);
        return RestBean.success();
    }

    @PostMapping("create-type")
    public RestBean<Void> createType(@RequestBody TopicTypeCreateVO vo){
        service.createTopicType(vo);
        return RestBean.success();
    }

    @GetMapping("/change-topic-type")
    public RestBean<Void> changeTopicType(@RequestParam int tid,
                                          @RequestParam int type){
        service.changeTopicType(tid, type);
        return RestBean.success();
    }
}
