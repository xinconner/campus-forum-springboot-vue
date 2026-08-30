package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.TopicComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TopicCommentMapper extends BaseMapper<TopicComment> {
}
