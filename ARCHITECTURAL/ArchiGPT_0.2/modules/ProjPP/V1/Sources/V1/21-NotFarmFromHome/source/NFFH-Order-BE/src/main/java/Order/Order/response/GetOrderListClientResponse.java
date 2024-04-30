package Order.Order.response;

import java.util.List;

public class GetOrderListClientResponse {
    private List<orderElement> orderList;

    public GetOrderListClientResponse(List<orderElement> orderList) {
        this.orderList = orderList;
    }

    public List<orderElement> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<orderElement> orderList) {
        this.orderList = orderList;
    }

    

}


/*
 * res: {
    orders: {
        order: string,
        orderpart: {
            seller: string,
            products: {
                product: string,
                price: string,
                quantity: string,
                withdrawn: boolean
            } []
        } [],
        commission: number,
        pickup: Date, (americana?)
        total: number,
        accepted: boolean,
    }[]
}
 */