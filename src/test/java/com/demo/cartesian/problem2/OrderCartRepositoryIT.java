package com.demo.cartesian.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderCartRepositoryIT {
    @Autowired
    private OrderCartRepository orderRepo;
    @Autowired
    private EntityManager entityManager;
    private Statistics stat;

    @BeforeEach
    public void setUp() {
        stat = entityManager.unwrap(Session.class).getSessionFactory().getStatistics();
    }

    // sum of noOfLines * noOfAttachments * noOfAudits for each orders.
    @Test
    public void findOrderByOrderIdBetween() {
        List<Order> orders = orderRepo.findOrderByOrderIdBetween(1l, 10l);
        assertEquals(10000, orders.size());
        assertEquals(310, stat.getEntityLoadCount());
        assertEquals(1, stat.getPrepareStatementCount());
    }

    // returns 10 orders, but query run is the same as above. so db i think returns 10000 orders.
    @Test
    public void findOrderByOrderIdBetween_returnsSet() {
        Set<Order> orders = orderRepo.findOrderByOrderIdBetween_returnsSet(1l, 10l);
        assertEquals(10, orders.size());
        assertEquals(310, stat.getEntityLoadCount());
        assertEquals(1, stat.getPrepareStatementCount());
    }

    @Test
    public void findOrderByOrderIdBetween_useDistinct() {
        List<Order> orders = orderRepo.findOrderByOrderIdBetween_useDistinct(1l, 10l);
        assertEquals(10, orders.size());
        assertEquals(310, stat.getEntityLoadCount());
        assertEquals(0, stat.getCollectionFetchCount());
        assertEquals(1, stat.getPrepareStatementCount());
    }
}