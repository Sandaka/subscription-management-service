package com.kingston.msc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/16/22
 */
@Entity
@Table(name = "sms_user_detail")
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SmsUserDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_pkg_code", nullable = false)
    private String userPkgCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sms_user_id", referencedColumnName = "id", nullable = false)
    private SmsUser smsUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sms_role_id", referencedColumnName = "id", nullable = false)
    private SmsRole smsRoleId;

    @Embedded
    private Audit audit;
}
