package com.shopping.integral.repository;

import com.shopping.integral.dao.model.IntegralOrderHook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHookRepository extends JpaRepository<IntegralOrderHook, Long> {

}