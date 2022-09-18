package com.kingston.msc.model;

import lombok.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/28/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class LoginUserDetail {

    private String smsUserId;
    private String username;
    private String password;
    private String packageCode;
    private String smsAccountId;
    private String statusMsg;
}
