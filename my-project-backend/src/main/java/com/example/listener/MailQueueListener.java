package com.example.listener;

import com.example.entity.dto.EmailRecord;
import com.example.mapper.EmailRecordMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于处理邮件发送的消息队列监听器
 */
@Slf4j
@Component
@RabbitListener(queues = "mail")
public class MailQueueListener {

    @Resource
    JavaMailSender sender;

    @Resource
    EmailRecordMapper recordMapper;

    @Value("${spring.mail.username}")
    String username;

    /**
     * 处理邮件发送
     * @param email 邮件信息
     */
    @RabbitHandler
    public void sendMailMessage(EmailRecord email) {
        try {
            sender.send(createMessage(email));
            email.setStatus(1);
            recordMapper.updateById(email);
            log.info("邮件发送成功，邮件记录ID；{}，邮件接收人：{}",email.getId(),email.getStatus());
        }catch (Exception e){
            log.info("邮件发送成功，邮件记录ID；{}，邮件接收人：{}，错误信息：{}",email.getId(),email.getStatus(),e.getMessage());
            throw e;
        }

    }

    /**
     * 快速封装简单邮件消息实体
     * @param email 邮件记录
     * @return 邮件实体
     */
    private SimpleMailMessage createMessage(EmailRecord email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(email.getTitle());
        message.setText(email.getContent());
        message.setTo(email.getEmail());
        message.setFrom(username);
        return message;
    }
}
