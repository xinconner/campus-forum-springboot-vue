package com.example.controller.admin;

import com.example.entity.PageRestBean;
import com.example.entity.RestBean;
import com.example.entity.dto.EmailRecord;
import com.example.service.EmailService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Vector;

@RestController
@RequestMapping("api/admin/email")
public class EmailAdminController {
    @Resource
    EmailService emailService;

    @GetMapping("/list")
    public PageRestBean<EmailRecord> listEmailRecord(@RequestParam int page,
                                                     @RequestParam int size){
        return PageRestBean.success(emailService.listEmailRecord(page, size));
    }

    @GetMapping("/resend")
    public RestBean<Void> resendEmailRecord(@RequestParam int id){
        if (emailService.resendEmailRecord(id)) {
            return RestBean.success();
        }else {
            return RestBean.failure(400,"邮件重发失败");
        }
    }
}
