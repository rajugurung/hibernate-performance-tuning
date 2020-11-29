package com.demo.cartesian.solution;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.demo.cartesian.problem.OrderCart;

import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderCartDao {
    private EntityManager em;

    @Autowired
    public OrderCartDao(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public List<OrderCartS> findOrders() {
        List<OrderCartS> orders = em.createQuery("Select distinct o from OrderCartS o " + 
                        "left join fetch o.lines " + 
                        "where o.id >= :fromOrdreId and o.id <= :toOrderId", OrderCartS.class)
                .setParameter("fromOrdreId", 1l)
                .setParameter("toOrderId", 10l)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        orders = em.createQuery("Select distinct o from OrderCartS o left join fetch o.attachments where o in :orders",OrderCartS.class)
                .setParameter("orders", orders)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        orders = em.createQuery("Select distinct o from OrderCartS o left join fetch o.audits where o in :orders",OrderCartS.class)
                .setParameter("orders", orders)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();

        
        return orders;
    }
}