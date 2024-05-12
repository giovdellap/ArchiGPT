package Order.Order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Order.Order.entities.Order;
import Order.Order.entities.OrderPart;


@Repository
public interface OrderPartRepository extends JpaRepository<OrderPart, Integer>{
    List<OrderPart> findByidOrder(int orderID);

    List<OrderPart> findBySeller(int sellerID);
}
