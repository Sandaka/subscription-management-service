package com.kingston.msc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/16/22
 */
@Entity
@Table(name = "sms_user")
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SmsUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date userCreationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sms_account_id", referencedColumnName = "id", nullable = false)
    private SmsAccount smsAccountId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<SmsUserDetail> smsUserDetailSet;

    @Embedded
    private Audit audit;
}
