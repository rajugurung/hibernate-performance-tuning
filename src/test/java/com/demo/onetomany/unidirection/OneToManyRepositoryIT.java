package com.demo.onetomany.unidirection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OneToManyRepositoryIT {
    @Resource
    private OneToManyRepository repo;
    @Resource
    private EntityManager entityManager;
    private Statistics stat;

    @BeforeEach
    public void setUp() {
        stat = entityManager.unwrap(Session.class).getSessionFactory().getStatistics();
    }
    
    @Test
    public void test() {
        Line line1 = Line.builder().lineNbr("1").build();
        Line line2 = Line.builder().lineNbr("2").build();
        Line line3 = Line.builder().lineNbr("3").build();
        Order order = Order.builder().orderNbr("1").lines(new ArrayList<>()).build();
        order.addLine(line1);
        order.addLine(line2);
        order.addLine(line3);

        repo.saveAndFlush(order);
        System.out.println("update count: " + stat.getEntityUpdateCount());
        assertEquals(4, stat.getEntityInsertCount());
        //4 inserts, 3 updates, 4 seq.nextVal
        assertEquals(11, stat.getPrepareStatementCount());
        
    }

}
