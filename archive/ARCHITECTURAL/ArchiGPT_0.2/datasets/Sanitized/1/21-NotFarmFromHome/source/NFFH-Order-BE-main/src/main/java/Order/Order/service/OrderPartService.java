package Order.Order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Order.Order.entities.Order;
import Order.Order.entities.OrderPart;
import Order.Order.repository.OrderPartRepository;
import Order.Order.repository.OrderRepository;

@Service
public class OrderPartService implements IOrderPartService{
    
    @Autowired
    private final OrderPartRepository orderPartRepository;

    public OrderPartService(OrderPartRepository orderPartRepository){
        this.orderPartRepository = orderPartRepository;
    }


    public OrderPart create(OrderPart orderPart) {
        return this.orderPartRepository.save(orderPart);
    }
}
