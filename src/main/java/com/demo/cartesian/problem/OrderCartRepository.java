package com.demo.cartesian.problem;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
    
    // returns 11085 records
    @Query(value = "Select o from OrderCart o left join fetch o.lines left join fetch o.audits left join fetch o.attachments " +
                    "where o.id >= :fromOrdreId and o.id <= :toOrderId")
    List<OrderCart> findOrderByOrderIdBetween(@Param("fromOrdreId") Long fromOrderId,
            @Param("toOrderId") Long toOrderId);
    
    // returns 10 orders, the sql query generated is same. So i think db still returns 11085 records, java removes duplicates
    @Query(value = "Select o from OrderCart o left join fetch o.lines left join fetch o.audits left join fetch o.attachments " +
                    "where o.id >= :fromOrdreId and o.id <= :toOrderId")
    Set<OrderCart> findOrderByOrderIdBetween_returnsSet(@Param("fromOrdreId") Long fromOrderId,
            @Param("toOrderId") Long toOrderId);
    
    // returns 10 orders using distinct, but distincts are usually more expensive
    // https://webbtechsolutions.com/2009/07/24/the-effects-of-distinct-in-a-sql-query/#:~:text=The%20fact%20that%20the%20resultset,is%20returned%20to%20the%20user.
    @Query(value = "Select distinct o from OrderCart o left join fetch o.lines left join fetch o.audits left join fetch o.attachments " +
            "where o.id >= :fromOrdreId and o.id <= :toOrderId")
    List<OrderCart> findOrderByOrderIdBetween_useDistinct(@Param("fromOrdreId") Long fromOrderId, @Param("toOrderId") Long toOrderId);              
    
}