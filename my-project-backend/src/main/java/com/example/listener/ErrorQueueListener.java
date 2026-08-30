package com.example.listener;

import com.example.entity.dto.EmailRecord;
import com.example.mapper.EmailRecordMapper;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = Const.MQ_ERROR)
public class ErrorQueueListener {

    @Resource
    EmailRecordMapper recordMapper;

    @RabbitHandler
    public void saveErrorToDatabase(EmailRecord record) {
        log.error("出现一条错误的邮件发送信息: {}", record);
        record.setStatus(2);
        recordMapper.updateById(record);
    }
}
