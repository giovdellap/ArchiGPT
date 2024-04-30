package Order.Order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Order.Order.entities.Order;
import Order.Order.repository.OrderRepository;



@Service
public class OrderService implements IOrderService{

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }



    @Override
    public Order create(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public List<Order> findByClient(int clientID) {
        return this.orderRepository.findByClient(clientID);
        
    }

    @Override
    public void delete(int id) {
        
        this.orderRepository.deleteById(id);
        return ;

    }

    
}
