package Order.Order.requests;

import Order.Order.entities.Order;
import Order.Order.entities.OrderPart;

public record CreateOrderInput(float commission, int client, String pickup, float total, String accepted, CreateOrderPart[] orderPart) {
    public Order ToOrder(){
        Order order = new Order();
        
        order.setClient(client);
        order.setCommission(commission);
        order.setDate(pickup);
        order.setTotal(total);
        order.setAccepted(true);

        return order;
    }

    public CreateOrderPart[] getorderPartList(){
        return orderPart;
    }
}


