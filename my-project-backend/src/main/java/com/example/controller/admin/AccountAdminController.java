package com.example.controller.admin;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.RestBean;
import com.example.entity.dto.Account;
import com.example.entity.dto.AccountDetails;
import com.example.entity.dto.AccountPrivacy;
import com.example.entity.vo.response.AccountVO;
import com.example.service.AccountDetailsService;
import com.example.service.AccountPrivacyService;
import com.example.service.AccountService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/admin/user")
public class AccountAdminController {

    @Resource
    AccountService service;

    @Resource
    AccountDetailsService detailsService;

    @Resource
    AccountPrivacyService privacyService;

    @Resource
    StringRedisTemplate template;

    @Value("${spring.security.jwt.expire}")
    private int expire;

    @GetMapping("/list")
    public RestBean<JSONObject> accountList(@RequestParam int page,
                                            @RequestParam int size,
                                            @RequestParam(required = false) String keyword){
        JSONObject object = new JSONObject();
        Page<Account> accountPage = service.page(Page.of(page, size), Wrappers.<Account>query()
                .eq(keyword!=null,"id",keyword).or()
                .like(keyword!=null,"username","%"+keyword+"%")
        );
        List<AccountVO> list = accountPage
                .getRecords()
                .stream()
                .map(a -> a.asViewObject(AccountVO.class))
                .toList();
        object.put("total",accountPage.getTotal());
        object.put("list",list);
        return RestBean.success(object);
    }

    @GetMapping("/detail")
    public RestBean<JSONObject> accountDetail(int id){
        JSONObject object = new JSONObject();
        object.put("detail",detailsService.findAccountDetailsById(id));
        object.put("privacy",privacyService.accountPrivacy(id));
        return RestBean.success(object);
    }

    @PostMapping("/save")
    public RestBean<Void> saveAccount(@RequestBody JSONObject object,
                                      @RequestAttribute(Const.ATTR_USER_ID) int uid){
        int id=object.getInteger("id");
        if (uid==id){
            return RestBean.failure(400,"不能修改自己的帐户信息");
        }
        Account account=service.findAccountById(id);
        Account save=object.toJavaObject(Account.class);
        handleBanned(account,save);
        BeanUtils.copyProperties(save,account,"password","registerTime");
        service.saveOrUpdate(account);
        AccountDetails details = detailsService.findAccountDetailsById(id);
        AccountDetails accountDetails=object.getJSONObject("detail").toJavaObject(AccountDetails.class);
        BeanUtils.copyProperties(accountDetails,details);
        detailsService.saveOrUpdate(details);
        AccountPrivacy privacy=object.getJSONObject("privacy").toJavaObject(AccountPrivacy.class);
        AccountPrivacy accountPrivacy=privacyService.accountPrivacy(id);
        BeanUtils.copyProperties(accountDetails,privacy);
        privacyService.saveOrUpdate(accountPrivacy);
        return RestBean.success();
    }

    @PostMapping("/change-password")
    public RestBean<Void> changePassword(@RequestBody JSONObject object){
        service.modifyPassword(
                object.getInteger("id"),
                object.getString("newPassword")
        );
        return RestBean.success();
    }

    private void handleBanned(Account old,Account current){
        String key = Const.BANNED_BLOCK +old.getId();
        if (!old.isBanned() && current.isBanned()){
            template.opsForValue().set(key,"true",expire, TimeUnit.HOURS);
        }else if (old.isBanned() && !current.isBanned()){
            template.delete(key);
        }
    }
}
