package scs.iss.agentie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import scs.iss.agentie.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Transactional
    @Query(value="INSERT INTO orders(product_id, amount, customer_name) VALUES(?#{#order.productId}, ?#{#order.amount}}, ?#{#order.customerName}})", nativeQuery = true)
    void saveOrder(@Param("order") Order order);
}
