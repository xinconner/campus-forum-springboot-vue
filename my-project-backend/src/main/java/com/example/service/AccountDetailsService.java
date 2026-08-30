package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.AccountDetails;
import com.example.entity.vo.request.DetailsSaveVO;

public interface AccountDetailsService extends IService<AccountDetails> {
    //查询用户信息
    AccountDetails findAccountDetailsById(int id);
    //保存信息
    boolean saveAccountDetails(int id, DetailsSaveVO vo);
}
