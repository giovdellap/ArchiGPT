package Order.Order.requests;

import Order.Order.entities.OrderPart;

public record CreateOrderPart(int seller, String productList) {
    public OrderPart toOrderPart(){
        OrderPart orderPart = new OrderPart();

        orderPart.setIdOrder(0);
        orderPart.setSeller(seller);
        orderPart.setProductList(productList);

        return orderPart;

    } 
    
}


