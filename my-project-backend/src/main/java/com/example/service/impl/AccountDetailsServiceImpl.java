package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Account;
import com.example.entity.dto.AccountDetails;
import com.example.entity.vo.request.DetailsSaveVO;
import com.example.mapper.AccountDetailsMapper;
import com.example.service.AccountDetailsService;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountDetailsServiceImpl extends ServiceImpl<AccountDetailsMapper, AccountDetails> implements AccountDetailsService {

    @Resource
    AccountService service;

    @Override
    public AccountDetails findAccountDetailsById(int id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public synchronized boolean saveAccountDetails(int id, DetailsSaveVO vo) {
        Account account = service.findAccountByNameOrEmail(vo.getUsername());
        if (account==null||account.getId()==id){
            if(service.update()
                    .eq("id",id)
                    .set("username",vo.getUsername())
                    .update()) {
                this.saveOrUpdate(new AccountDetails(//如果数据库有数据就插入，没数据就更新
                        id,vo.getGender(), vo.getPhone(),
                        vo.getQq(), vo.getWx(), vo.getDesc()
                ));
                return true;
            }
        }
        return false;
    }
}
