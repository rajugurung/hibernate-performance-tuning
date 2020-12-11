package com.demo.nplusone.lazy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryS extends JpaRepository<Order, Long>{
    
}