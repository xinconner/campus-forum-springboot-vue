package com.example.entity.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class AccountVO {
    int id;
    String username;
    String email;
    String role;
    String avatar;
    Date registerTime;
    boolean mute;
    boolean banned;
}
