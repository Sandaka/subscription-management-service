package com.kingston.msc.repository;

import com.kingston.msc.entity.SubscriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/27/22
 */
@Repository
public interface SubscriptionDetailRepository extends JpaRepository<SubscriptionDetail, Long> {
}
