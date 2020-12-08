package com.demo.nplusone.problem;

import java.util.List;
import java.util.Set;

import com.demo.nplusone.solution.OrderDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustNbr(String custNbr);

    @Query(value = "from Order o left join fetch o.lines where o.custNbr = :custNbr")
    List<Order> findByCustNbrFetch(String custNbr);
    
    Order findByOrderNbr(String orderNbrs);

    //Query run: select order0_.id as col_0_0_, order0_.order_nbr as col_1_0_ from orders order0_ where order0_.id=?
    //Lines not fetched. So only 1 query run.
    @Query(value = "select new com.demo.nplusone.solution.OrderDto(o.id,o.orderNbr) from Order o where o.id = :id")
    OrderDto findOrderByIdDtoProjection(Long id);
    
    //solution to n+1 but returns 100 orders because list
    @Query(value = "from Order o left join fetch o.lines")
    List<Order> findOrdersAsList();

    //solution to n+1 but with distinct keyword. returns 10 orders
    @Query(value = "Select distinct o from Order o left join fetch o.lines")
    List<Order> findOrdersAsListWithDistinctKeyword();

    // solution to n+1 and returns 10 orders becaus set
    @Query(value = "from Order o left join fetch o.lines")
    Set<Order> findOrdersAsSet();

    @Query(value = "select * from orders o left join line l on o.id=l.order_id", nativeQuery = true)
    Set<Order> findOrdersNativeQuery();
}