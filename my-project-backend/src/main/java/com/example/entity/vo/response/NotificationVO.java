package com.example.entity.vo.response;

import lombok.Data;

@Data
public class NotificationVO {
    Integer id;
    String title;
    String content;
    String type;
    String url;
    String time;
}
