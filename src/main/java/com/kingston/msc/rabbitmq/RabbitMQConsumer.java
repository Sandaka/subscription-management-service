package com.kingston.msc.rabbitmq;

import com.kingston.msc.entity.*;
import com.kingston.msc.entity.Package;
import com.kingston.msc.model.CPTransactionTracker;
import com.kingston.msc.model.StudentTransactionTracker;
import com.kingston.msc.repository.*;
import com.netflix.discovery.converters.Auto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/27/22
 */
@Service
public class RabbitMQConsumer {

//    @Value("${learngenix.rabbitmq.queue}")
//    private static final String QUEUE = "${learngenix.rabbitmq.queue}";

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SmsAccountRepository smsAccountRepository;

    @Autowired
    private SubscriptionPaymentRepository subscriptionPaymentRepository;

    @Autowired
    private SubscriptionDetailRepository subscriptionDetailRepository;

    @Autowired
    private SmsUserRepository smsUserRepository;

    @Autowired
    private SmsUserDetailRepository smsUserDetailRepository;

    @Autowired
    private CourseProviderRabbitMQSender courseProviderRabbitMQSender;

    @Autowired
    private StudentRabbitMQSender studentRabbitMQSender;

    @RabbitListener(queues = {"${learngenix.rabbitmq.cp.registration.queue}"})
    public void receiveCourseProverRegistrationMessage(CPTransactionTracker cpTransactionTracker) {
        System.out.println("Received Message from course p Queue CP id >>" + cpTransactionTracker.getCourseProviderId());
        processCourseProviderSubscription(cpTransactionTracker);
    }

    @RabbitListener(queues = {"${learngenix.rabbitmq.stu.registration.queue}"})
    public void receiveStudentRegistrationMessage(StudentTransactionTracker studentTransactionTracker) {
        System.out.println("Received Message from student Queue Stu id >>" + studentTransactionTracker.getStudentId());
        processStudentSubscription(studentTransactionTracker);
    }

    @Transactional
    protected void processCourseProviderSubscription(CPTransactionTracker cpTransactionTracker) {
        // sms account
        SmsAccount smsAccount = new SmsAccount();
        smsAccount.setAccountName("test account");
        smsAccount.setAccountUrl("");

        Audit audit = new Audit();
        audit.setStatus(1);
        audit.setCreatedDate(new Date());
        audit.setLastEditDate(new Date());
        smsAccount.setAudit(audit);

        Package package_ = new Package();
        package_.setId(1L);
        smsAccount.setPackageId(package_);

        smsAccount.setSchoolName("Esoft metro campus");

        smsAccount = smsAccountRepository.save(smsAccount);

        // subscription payment
        SubscriptionPayment subscriptionPayment = new SubscriptionPayment();
        subscriptionPayment.setAmount(new BigDecimal(29));
        subscriptionPayment.setAudit(audit);
        subscriptionPayment.setPaymentDate(new Date());
        subscriptionPayment.setSmsAccountId(smsAccount);
        subscriptionPayment.setTransactionDetail(cpTransactionTracker.getTransactionId());

        subscriptionPayment = subscriptionPaymentRepository.save(subscriptionPayment);

        // subscription detail
        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
        subscriptionDetail.setAudit(audit);
        subscriptionDetail.setRegisteredDate(new Date());
        subscriptionDetail.setSmsAccountId(smsAccount);
        Subscription subscription = subscriptionRepository.findSubscriptionBySubscriptionName(cpTransactionTracker.getSubscription());
        subscriptionDetail.setSubscriptionId(subscription);

        subscriptionDetail = subscriptionDetailRepository.save(subscriptionDetail);

        // sms user
        SmsUser smsUser = new SmsUser();
        smsUser.setAudit(audit);
        smsUser.setPassword("1234");
        smsUser.setSmsAccountId(smsAccount);
        smsUser.setUserCreationDate(new Date());
        smsUser.setUsername(cpTransactionTracker.getEmail());

        smsUser = smsUserRepository.save(smsUser);

        //sms user detail
        SmsUserDetail smsUserDetail = new SmsUserDetail();
        smsUserDetail.setAudit(audit);
        SmsRole smsRole = new SmsRole();
        smsRole.setId(2L); // cp admin role
        smsUserDetail.setSmsRoleId(smsRole);
        smsUserDetail.setSmsUserId(smsUser);
        smsUserDetail.setUserPkgCode("CPAB"); //

        smsUserDetail = smsUserDetailRepository.save(smsUserDetail);

        cpTransactionTracker.setSmsAccountId(smsAccount.getId().toString());
        cpTransactionTracker.setPassword(smsUser.getPassword());
        cpTransactionTracker.setUsername(smsUser.getUsername());
        publishCpRegistrationSuccessMessage(cpTransactionTracker);
    }

    @Transactional
    protected void processStudentSubscription(StudentTransactionTracker studentTransactionTracker) {

        SmsUser smsUser = new SmsUser();
        smsUser.setUsername(studentTransactionTracker.getEmail());
        smsUser.setUserCreationDate(new Date());
        smsUser.setPassword("1234");
        SmsAccount smsAccount = new SmsAccount();
        smsAccount.setId(studentTransactionTracker.getSmsAccountId());
        smsUser.setSmsAccountId(smsAccount);
        Audit audit = new Audit();
        audit.setStatus(1);
        audit.setCreatedDate(new Date());
        audit.setLastEditDate(new Date());
        smsUser.setAudit(audit);

        smsUser = smsUserRepository.save(smsUser);

        SmsUserDetail smsUserDetail = new SmsUserDetail();
        smsUserDetail.setUserPkgCode("STUB");
        smsUserDetail.setSmsUserId(smsUser);
        SmsRole smsRole = new SmsRole();
        smsRole.setId(3L);
        smsUserDetail.setSmsRoleId(smsRole);
        smsUserDetail.setAudit(audit);

        smsUserDetail = smsUserDetailRepository.save(smsUserDetail);

        studentTransactionTracker.setPassword(smsUser.getPassword());
        studentTransactionTracker.setSmsUserId(smsUser.getId());
        studentTransactionTracker.setUsername(smsUser.getUsername());

        publishStudentRegistrationSuccessMessage(studentTransactionTracker);
    }

    private void publishCpRegistrationSuccessMessage(CPTransactionTracker transactionTracker) {
        courseProviderRabbitMQSender.sendToCourseProviderQueue(transactionTracker);
    }

    private void publishStudentRegistrationSuccessMessage(StudentTransactionTracker studentTransactionTracker) {
        studentRabbitMQSender.sendToStudentQueue(studentTransactionTracker);
    }
}
