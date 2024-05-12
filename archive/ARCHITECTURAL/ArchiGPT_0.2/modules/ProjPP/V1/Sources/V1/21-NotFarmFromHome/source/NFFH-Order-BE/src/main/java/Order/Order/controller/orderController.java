package Order.Order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Order.Order.entities.Order;
import Order.Order.entities.OrderPart;
import Order.Order.repository.OrderPartRepository;
import Order.Order.repository.OrderRepository;
import Order.Order.requests.CreateOrderInput;
import Order.Order.requests.CreateOrderPart;
import Order.Order.response.CreateOrderResponse;
import Order.Order.response.GetOrderListClientResponse;
import Order.Order.response.orderElement;
import Order.Order.service.OrderPartService;
import Order.Order.service.OrderService;

@RestController
public class orderController {
    
    @Autowired
    public final OrderService orderService;
    public final OrderPartService orderPartService;
    public final OrderRepository orderRepository;
    public final OrderPartRepository orderPartRepository;
    
    
    //ADD A PRODUCT 


    public orderController(OrderService orderService, OrderPartService orderPartService,
            OrderRepository orderRepository, OrderPartRepository orderPartRepository) {
        this.orderService = orderService;
        this.orderPartService = orderPartService;
        this.orderRepository = orderRepository;
        this.orderPartRepository = orderPartRepository;
    }


    @PostMapping("/order")
	public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderInput createOrderInput){
		
		Order order = createOrderInput.ToOrder();

		this.orderService.create(order);

        CreateOrderResponse response = new CreateOrderResponse(true, String.valueOf(order.getId()));

        CreateOrderPart[] createOrderPart = createOrderInput.getorderPartList();
        
        OrderPart orderPart;

        for (int i = 0; i < 2; i++){
            orderPart = createOrderPart[i].toOrderPart();
            orderPart.setIdOrder(order.getId());
            this.orderPartService.create(orderPart);
        }

        return new ResponseEntity<CreateOrderResponse>(response, HttpStatus.CREATED);

    }


    //GET ORDER LIST OF A SINGLE CUSTOMER

    @GetMapping("/order/client/myorder")
    public ResponseEntity<GetOrderListClientResponse> getOrderByClientID(@RequestParam int id){
        
        List<Order> orderList = this.orderService.findByClient(id);
        List<OrderPart> orderPartList;
        List<orderElement> orderElementList = new ArrayList<orderElement>();

        for (int i = 0; i < orderList.size(); i++){
            
            
            Order order = orderList.get(i);
            int orderID = order.getId();
            orderPartList = this.orderPartRepository.findByidOrder(orderID);

            System.out.println(orderPartList.toString());
            
            orderElement orderElem = new orderElement(orderID, orderPartList, i, order.getDate(), order.getTotal(), order.isAccepted());

            orderElementList.add(orderElem);
        }

        GetOrderListClientResponse response = new GetOrderListClientResponse(orderElementList);

        
        return new ResponseEntity<GetOrderListClientResponse>(response, HttpStatus.OK);
    }

    //Get all order performed by a farmer

    @GetMapping("/order/farmer/myorder")
    public ResponseEntity<List<OrderPart>> getOrderByFarmerID(@RequestParam int id){
    
        List<OrderPart> orderPartList = this.orderPartRepository.findBySeller(id);
        
        return new ResponseEntity<List<OrderPart>>(orderPartList, HttpStatus.OK);
    }


}
