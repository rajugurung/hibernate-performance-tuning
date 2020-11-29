package com.demo.nplusone.problem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import com.demo.nplusone.solution.OrderDto;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderRepositoryIT {
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private EntityManager entityManager;
    private Statistics stat;

    @BeforeEach
    public void setUp() {
        stat = entityManager.unwrap(Session.class).getSessionFactory().getStatistics();
    }

    @Test
    public void countNoOfOrders() {
        assertEquals(10, orderRepository.count());
        assertEquals(1, stat.getPrepareStatementCount());
        assertEquals(0, stat.getEntityLoadCount());
    }

    // You only want order data, but you get lines too. 
    @Test
    public void findById() {
        Optional<Order> order = orderRepository.findById(1l);
        assertEquals(11, stat.getEntityLoadCount());//left joins order and line
        assertEquals(1, stat.getPrepareStatementCount());
    }

    
    //Runs only 1 query and doesnot fetch lines. NO entities loaded.
    @Test
    public void findOrderByIdDtoProjection() {
        OrderDto order = orderRepository.findOrderByIdDtoProjection(1l);
        assertNotNull(order);
        assertEquals(1, stat.getPrepareStatementCount());
        assertEquals(0, stat.getEntityLoadCount());
    }

    // n+1 issue
    @Test
    public void findByOrderNbr() {
        List<Order> orders = orderRepository.findByOrderNbr("order-nbr-1");
        assertEquals(2, orders.size());
        assertEquals(23, stat.getEntityLoadCount());
        assertEquals(3, stat.getPrepareStatementCount());
    }

    // n+1 issue
    @Test
    public void test() {
        List<Order> orders = orderRepository.findAll();
        assertEquals(10, orders.size());
        assertEquals(110, stat.getEntityLoadCount());
        assertEquals(10, stat.getCollectionFetchCount());//N
        assertEquals(1, stat.getQueryExecutionCount());// + 1
        assertEquals(11, stat.getPrepareStatementCount());//11
    }

    //n+1 solution, runs only one query but there are 100 orders
    @Test
    public void findOrdersAsList() {
        List<Order> orders = orderRepository.findOrdersAsList();
        assertEquals(1, stat.getPrepareStatementCount());
        assertEquals(0, stat.getCollectionFetchCount());
        assertEquals(100, orders.size());
        assertEquals(110, stat.getEntityLoadCount());
    }

    // n+1 solution, runs only one query and 10 orders returned
    // Using distinct key is probably more efficient than using set like below. Because db probably
    // returns 100 records and java removes duplicates. Where as with 'distinct' keyword, db probably
    // removes the duplicate. So there is no overhead of transfering more records across the wire. 
    @Test
    public void findOrdersAsListWithDistinctKeyword() {
        List<Order> orders = orderRepository.findOrdersAsListWithDistinctKeyword();
        assertEquals(1, stat.getPrepareStatementCount());
        assertEquals(0, stat.getCollectionFetchCount());
        assertEquals(0, stat.getCollectionFetchCount());
        assertEquals(10, orders.size());
        assertEquals(110, stat.getEntityLoadCount());
    }

    // n+1 solution, using set, runs one query and there are only 10 orders.
    @Test
    public void findOrdersAsSet() {
        Set<Order> orders = orderRepository.findOrdersAsSet();
        assertEquals(1, stat.getPrepareStatementCount());
        assertEquals(10, orders.size());
        assertEquals(110, stat.getEntityLoadCount());
    }


    /* 
        Although this is a native query, it still runs 11 queries, and loads 110 entities
        probably because the returned data is of type "Order" entity. 
    
        Handle this issue in different section. Solution can be found here
        https://vladmihalcea.com/one-to-many-dto-projection-hibernate/
     */
    @Test
    public void findOrdersNativeQuery() {
        Set<Order> orders = orderRepository.findOrdersNativeQuery();
        assertEquals(11, stat.getPrepareStatementCount());
        assertEquals(10, orders.size());
        assertEquals(110, stat.getEntityLoadCount());
    }

}
