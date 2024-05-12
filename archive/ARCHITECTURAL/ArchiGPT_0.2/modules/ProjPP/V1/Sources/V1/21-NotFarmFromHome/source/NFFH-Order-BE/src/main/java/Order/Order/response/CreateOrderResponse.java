package Order.Order.response;

public class CreateOrderResponse {

    private boolean success;
    private String orderID;
    
    
    public CreateOrderResponse(boolean success, String orderID) {
        this.success = success;
        this.orderID = orderID;
    }


    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getOrderID() {
        return orderID;
    }


    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    @Override
    public String toString() {
        return "CreateOrderResponse [success=" + success + ", orderID=" + orderID + "]";
    }

    
    
    
}
