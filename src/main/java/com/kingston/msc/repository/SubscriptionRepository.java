package com.kingston.msc.repository;

import com.kingston.msc.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/27/22
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findSubscriptionBySubscriptionName(String name);
}
