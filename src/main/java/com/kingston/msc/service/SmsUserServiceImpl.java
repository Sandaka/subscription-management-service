package com.kingston.msc.service;

import com.kingston.msc.model.LoginUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/28/22
 */
@Service
@Slf4j
public class SmsUserServiceImpl implements SmsUserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LoginUserDetail findLoginUser(String username, String password) {

        Query query = entityManager.createNativeQuery("SELECT su.id, su.username, su.sms_account_id, " +
                "sud.user_pkg_code " +
                "FROM learngenix_sms.sms_user su " +
                "INNER JOIN learngenix_sms.sms_user_detail sud ON sud.sms_user_id=su.id " +
                "WHERE su.username=(?) AND su.password=(?)");

        query.setParameter(1, username);
        query.setParameter(2, password);
        List<Object[]> list = query.getResultList();

        LoginUserDetail loginUserDetail = new LoginUserDetail();

        if (!list.isEmpty()) {
            loginUserDetail = new LoginUserDetail();
            for (Object[] obj : list) {
                loginUserDetail.setSmsUserId(obj[0].toString());
                loginUserDetail.setUsername(obj[1].toString());
                loginUserDetail.setSmsAccountId(obj[2].toString());
                loginUserDetail.setPackageCode(obj[3].toString());
                loginUserDetail.setStatusMsg("SUCCESS");
            }
        } else {
            loginUserDetail.setStatusMsg("FAILED");
        }
        return loginUserDetail;
    }
}
