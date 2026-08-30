package com.example.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Interact;
import com.example.entity.dto.Topic;
import com.example.entity.dto.TopicType;
import com.example.entity.vo.request.AddCommentVO;
import com.example.entity.vo.request.TopicCreateVO;
import com.example.entity.vo.request.TopicTypeCreateVO;
import com.example.entity.vo.request.TopicUpdateVO;
import com.example.entity.vo.response.*;

import java.util.List;

public interface TopicService  extends IService<Topic> {
    List<TopicType> listTypes();
    void updateTopicType(TopicTypeVO vo);
    void deleteTopicType(int id);
    void createTopicType(TopicTypeCreateVO vo);
    void changeTopicType(int tid,int type);
    String createTopic(int uid, TopicCreateVO vo);
    JSONObject listAllTopicByPage(int page, int size,String keyword);
    List<TopicPreviewVO> listTopicByPage(int page,int type);
    List<TopicTopVO> listTopTopics();
    TopicDetailVO getTopic(int tid,int uid);
    void interact(Interact interact,boolean state);
    List<TopicPreviewVO> listTopicCollects(int uid);
    String updateTopic(int id, TopicUpdateVO vo);
    String createComment(int uid, AddCommentVO vo);
    List<CommentVO> comments(int tid,int pageNumber);
    void deleteComment(int id,int uid);
    void deleteTopic(int id);
    void deleteTopic(int tid,int uid);
    void setTopicTop(int tid,boolean top);
    void setTopicLocked(int tid,boolean locked);
    void setTopicInvisible(int tid,boolean invisible);
    List<Topic> listTopicByUser(int uid);
    List<TopicSearchVO> searchTopic(String keyword);
}
