package com.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.dto.EmailRecord;
import com.example.mapper.EmailRecordMapper;
import com.example.service.EmailService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    AmqpTemplate rabbitTemplate;

    @Resource
    EmailRecordMapper recordMapper;

    @Override
    public void sendVerifyEmail(String type, String email, int code) {
        EmailRecord emailRecord = switch (type){
            case "register" ->new EmailRecord(email,"欢迎注册我们的网站",
                    "您的邮件注册验证码为: "+code+"，有效时间3分钟，为了保障您的账户安全，请勿向他人泄露验证码信息。");

            case "reset" ->new EmailRecord(email,"您的密码重置邮件",
                    "您好，您正在执行重置密码操作，验证码: "+code+"，有效时间3分钟，如非本人操作，请无视。");

            case "modify" ->new EmailRecord(email,"您的邮件修改验证邮件",
                    "您好，您正在绑定新的电子邮件地址，验证码："+code+"，有效时间3分钟，如非本人操作，请无视。");

            default -> throw new IllegalArgumentException(type);
        };
        recordMapper.insert(emailRecord);
        rabbitTemplate.convertAndSend(Const.MQ_MAIL, emailRecord);
    }

    @Override
    public Page<EmailRecord> listEmailRecord(int page, int size) {
        return recordMapper.selectPage(Page.of(page, size, true), Wrappers.emptyWrapper());
    }

    @Override
    public boolean resendEmailRecord(int id) {
        EmailRecord record = recordMapper.selectById(id);
        if(record == null){
            return false;
        }
        record.setStatus(0);
        rabbitTemplate.convertAndSend(Const.MQ_MAIL, record);//推到消息队列
        recordMapper.updateById(record);
        return true;
    }
}
