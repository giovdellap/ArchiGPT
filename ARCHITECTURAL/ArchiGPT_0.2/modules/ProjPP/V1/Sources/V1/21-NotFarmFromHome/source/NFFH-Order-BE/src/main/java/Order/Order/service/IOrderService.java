package Order.Order.service;

import java.util.List;

import Order.Order.entities.Order;

public interface IOrderService {

    Order create(Order order);

    List<Order> findByClient(int clientID);
    
    void delete(int id);
}
