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
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    public void afterEach() {
        // needed because stats accumulates after each test. 
        stat.clear();
    }

    // You only want order data, but you get lines too. 
    // select * from order left outer join line 
    // no n+1 issue
    @Test
    public void findById() {
        Optional<Order> order = orderRepository.findById(1l);
        assertEquals(11, stat.getEntityLoadCount());//left joins order and line
        assertEquals(1, stat.getPrepareStatementCount());
    }

    // n+1 issue on a single order query
    @Test
    public void findByOrderNbr() {
        Order order = orderRepository.findByOrderNbr("order-nbr-1");
        assertEquals(11, stat.getEntityLoadCount());
        assertEquals(2, stat.getPrepareStatementCount());
    }

    // n+1 issue
    @Test
    public void findAll() {
        List<Order> orders = orderRepository.findAll();
        assertEquals(10, orders.size());
        assertEquals(110, stat.getEntityLoadCount());
        assertEquals(10, stat.getCollectionFetchCount());//N
        assertEquals(1, stat.getQueryExecutionCount());// + 1
        assertEquals(11, stat.getPrepareStatementCount());//11
    }

    // n+1 on a list of orders query
    @Test
    public void findByCustNbr() {
        List<Order> orders = orderRepository.findByCustNbr("1a");
        assertEquals(4, orders.size());
        assertEquals(5, stat.getPrepareStatementCount());
        assertEquals(45, stat.getEntityLoadCount());
    }

    @Test
    public void findByCustNbrFetch() {
        List<Order> orders = orderRepository.findByCustNbrFetch("1a");
        assertEquals(4, orders.size());
        assertEquals(1, stat.getPrepareStatementCount());
        assertEquals(45, stat.getEntityLoadCount());
    }

    //Runs only 1 query and doesnot fetch lines. NO entities loaded.
    @Test
    public void findOrderByIdDtoProjection() {
        OrderDto order = orderRepository.findOrderByIdDtoProjection(1l);
        assertNotNull(order);
        assertEquals(1, stat.getPrepareStatementCount());
        assertEquals(0, stat.getEntityLoadCount());
    }

    // n+1 solution, runs only one query and 10 orders returned
    // Using distinct key is probably more efficient than using set like below. Because db probably
    // returns 100 records and java removes duplicates. Where as with 'distinct' keyword, db probably
    // removes the duplicate. So there is no overhead of transfering more records across the wire. 
    @Test
    public void findOrdersAsListWithDistinctKeyword() {
        List<Order> orders = orderRepository.findOrdersAsListWithDistinctKeyword();
        assertEquals(1, stat.getPrepareStatementCount());
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
