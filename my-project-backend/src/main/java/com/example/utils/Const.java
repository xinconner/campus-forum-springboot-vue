package com.example.utils;

/**
 * 一些常量字符串整合
 */
public final class Const {
    //JWT令牌
    public final static String JWT_BLACK_LIST = "jwt:blacklist:";
    public final static String JWT_FREQUENCY = "jwt:frequency:";
    //请求频率限制
    public final static String FLOW_LIMIT_COUNTER = "flow:counter:";
    public final static String FLOW_LIMIT_BLOCK = "flow:block:";
    public final static String BANNED_BLOCK = "banned:block:";
    //邮件验证码
    public final static String VERIFY_EMAIL_LIMIT = "verify:email:limit:";
    public final static String VERIFY_EMAIL_DATA = "verify:email:data:";
    //过滤器优先级
    public final static int ORDER_FLOW_LIMIT = -101;
    public final static int ORDER_CORS = -102;
    //请求自定义属性
    public final static String ATTR_USER_ID = "userId";
    //消息队列
    public final static String MQ_MAIL = "mail";
    public final static String MQ_ERROR = "error";
    //用户角色
    public final static String ROLE_DEFAULT = "user";
    public final static String ROLE_ADMIN = "admin";
    //论坛相关
    public final static String FORUM_WEATHER_CACHE = "weather:cache:v2:";
    public final static String FORUM_IMAGE_COUNTER = "forum:image:";
    public final static String FORUM_TOPIC_CREATE_COUNTER = "forum:topic:create:";
    public final static String FORUM_TOPIC_COMMENT_COUNTER = "forum:topic:comment:";
    public final static String FORUM_TOPIC_PREVIEW_CACHE = "topic:preview:";
}
