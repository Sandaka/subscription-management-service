package com.kingston.msc.controller;

import com.kingston.msc.model.LoginUserDetail;
import com.kingston.msc.service.SmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/28/22
 */
@RestController
@RequestMapping("/sms")
public class SmsUserController {

    @Autowired
    private SmsUserService smsUserService;

    @PostMapping("/login")
    public ResponseEntity<LoginUserDetail> getLoginDetails(@RequestBody LoginUserDetail loginUserDetail) {
        LoginUserDetail userDetail = smsUserService.findLoginUser(loginUserDetail.getUsername(), loginUserDetail.getPassword());
        return ResponseEntity.ok().body(userDetail);
    }
}
