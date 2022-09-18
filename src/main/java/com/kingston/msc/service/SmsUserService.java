package com.kingston.msc.service;

import com.kingston.msc.model.LoginUserDetail;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/28/22
 */
public interface SmsUserService {

    LoginUserDetail findLoginUser(String username, String password);
}
