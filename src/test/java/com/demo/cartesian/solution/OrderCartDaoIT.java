package com.demo.cartesian.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// @DataJpaTest
@SpringBootTest
public class OrderCartDaoIT {
    @Autowired
    private OrderCartDao orderDao;
    @Autowired
    private EntityManager em;
    private Statistics stat;

    @BeforeEach
    public void before() {
        stat = em.unwrap(Session.class).getSessionFactory().getStatistics();
    }

    @Test
    public void findOrders() {
        List<OrderCartS> orders = orderDao.findOrders();
        assertEquals(10, orders.size());
        System.out.println("audit size: " + orders.get(0).getAudits().size());
        System.out.println("audit size: " + orders.get(0).getLines().size());
        System.out.println(stat.toString());
        assertEquals(310, stat.getEntityLoadCount());
        assertEquals(0, stat.getCollectionFetchCount());
        assertEquals(3, stat.getQueryExecutionCount());
        assertEquals(3, stat.getPrepareStatementCount());
    }
    
}