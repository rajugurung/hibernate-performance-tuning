package com.demo.onetomany.unidirection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("oneToManyUniDirectionRepository")
public interface OneToManyRepository extends JpaRepository<Order, Long> {
    // public interface OneToManyRepository  {
    
}
