package com.kingston.msc.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/25/22
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = CPTransactionTracker.class)
public class CPTransactionTracker {

    private Long id;
    private String courseProviderId;
    private String transactionId;
    private String smsAccountId;
    private String subscription;

    private String username;
    private String password;
    private String email;
}
