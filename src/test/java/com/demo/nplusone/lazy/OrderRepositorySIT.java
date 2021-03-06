package com.demo.nplusone.lazy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderRepositorySIT {
    @Resource
    private OrderRepositoryS orderRepositoryS;
    @Resource
    private EntityManager entityManager;
    private Statistics stat;

    @BeforeEach
    public void beforeEach() {
        stat = entityManager.unwrap(Session.class).getSessionFactory().getStatistics();
    }

    // does not join on line because it's lazy
    @Test
    public void test() {
        Optional<Order> order = orderRepositoryS.findById(1l);
        assertTrue(order.isPresent());
        assertEquals(1, stat.getEntityLoadCount());
        assertEquals(1, stat.getPrepareStatementCount());
    }
    
}