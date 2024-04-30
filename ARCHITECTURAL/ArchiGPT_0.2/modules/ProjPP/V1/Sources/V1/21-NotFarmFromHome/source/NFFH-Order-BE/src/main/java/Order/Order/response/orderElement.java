package Order.Order.response;

import java.util.List;

import Order.Order.entities.OrderPart;

public class orderElement {

    public int orderID;
    public List<OrderPart> orderPartList; 
    public float commission;
    public String pickup;
    public float total;
    public boolean accepted;
    
    
    public orderElement(int orderID, List<OrderPart> orderPartList, float commission, String pickup, float total,
            boolean accepted) {
        this.orderID = orderID;
        this.orderPartList = orderPartList;
        this.commission = commission;
        this.pickup = pickup;
        this.total = total;
        this.accepted = accepted;
    }


    public int getOrderID() {
        return orderID;
    }


    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    public List<OrderPart> getOrderPartList() {
        return orderPartList;
    }


    public void setOrderPartList(List<OrderPart> orderPartList) {
        this.orderPartList = orderPartList;
    }


    public float getCommission() {
        return commission;
    }


    public void setCommission(float commission) {
        this.commission = commission;
    }


    public String getPickup() {
        return pickup;
    }


    public void setPickup(String pickup) {
        this.pickup = pickup;
    }


    public float getTotal() {
        return total;
    }


    public void setTotal(float total) {
        this.total = total;
    }


    public boolean isAccepted() {
        return accepted;
    }


    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    
    

}



