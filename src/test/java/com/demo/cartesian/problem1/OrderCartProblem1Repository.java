package com.demo.cartesian.problem1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderCartProblem1Repository extends JpaRepository<Order, Long> {

    // @Query(value = "Select o from com.demo.cartesian.problem1.Order o left join fetch o.lines left join fetch o.audits left join fetch o.attachments " +
    //                 "where o.id >= :fromOrdreId and o.id <= :toOrderId")
    // List<Order> findOrderByOrderIdBetween(@Param("fromOrdreId") Long fromOrderId,
    //         @Param("toOrderId") Long toOrderId);

            @Query(value = "Select o from com.demo.cartesian.problem1.Order o left join fetch o.lines " +
            "where o.id >= :fromOrdreId and o.id <= :toOrderId")
List<Order> findOrderByOrderIdBetween(@Param("fromOrdreId") Long fromOrderId,
    @Param("toOrderId") Long toOrderId);

    //         @Query(value = "Select o from com.demo.cartesian.problem1.Order o left join fetch o.lines left join fetch o.attachments " +
    //         "where o.id >= :fromOrdreId and o.id <= :toOrderId")
    // List<Order> findOrderByOrderIdBetweenLinesNatt(@Param("fromOrdreId") Long fromOrderId,
    // @Param("toOrderId") Long toOrderId);
    
}
