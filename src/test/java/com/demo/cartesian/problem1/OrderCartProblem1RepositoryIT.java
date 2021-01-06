package com.demo.cartesian.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.loader.MultipleBagFetchException;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderCartProblem1RepositoryIT {
    @Autowired
    private OrderCartProblem1Repository repo;
    @Autowired
    private EntityManager entityManager;
    private Statistics stat;

    @BeforeEach
    public void setUp() {
        stat = entityManager.unwrap(Session.class).getSessionFactory().getStatistics();
    }

    @Test
    public void findOrderByOrderIdBetween() {
        try{
            repo.findOrderByOrderIdBetween(1l, 10l);
        } catch (Exception e) {
            assertTrue(e.getCause().getCause().getMessage().contains("cannot simultaneously fetch multiple bags"));
            System.out.println(e);
        }        
    }

    // @Test
    // public void findOrderByOrderIdBetweenLinesNatt() {
    //     List<Order> orders = repo.findOrderByOrderIdBetweenLinesNatt(1l, 10l);
    //     assertEquals(1, 1);
    // }
}
