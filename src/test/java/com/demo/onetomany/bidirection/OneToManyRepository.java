package com.demo.onetomany.bidirection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("oneToManyBiDirectionRepository")
public interface OneToManyRepository extends JpaRepository<Order, Long> {
    
}
