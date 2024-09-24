package com.ecommerce.order.controller;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.entity.Order;

import com.ecommerce.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    //Create Order: Endpoint to place a new order.
    //implement DTO
    @PostMapping(value = "/createOrder", consumes = "application/json")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest){
        Order order= orderService.createOrder(orderRequest);
        if (order==null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return ResponseEntity.ok(order);
    }

    //Get Order by ID: Retrieve the details of a specific order.
    @GetMapping("/getOrder/{id}")
    public ResponseEntity<Order> getOrderByID(@PathVariable int id){
        Optional<Order> orderByID = orderService.getOrderByID(id);
        if (orderByID.isPresent()){
            return ResponseEntity.ok(orderByID.get());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Get All Orders for a User: Retrieve all orders placed by a specific user.
    @GetMapping("/getAllOrders")  // send as requestParam!
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestParam("userId") String userId){
         List<Order> allOrders= orderService.getAllOrdersForUser(userId);
        if (allOrders == null || allOrders.size() == 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(allOrders);
    }

//    Update Order Status: Update the status of an order (e.g., PENDING, SHIPPED, CANCELLED).
    @PatchMapping("/updateOrder/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable int orderId){
      Order order = orderService.updateOrderStatus((long)orderId);
        if (order == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(order);
    }

    //Cancel Order: Allow a user to cancel an order if possible.
    @PatchMapping("/cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestParam("orderId") int orderId ){
        boolean status = orderService.cancelOrder(orderId);
            if (status){
                return ResponseEntity.ok("Order canceled!");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot cancel order as order is either confirmed or shipped!");
    }

    //Track Order: Provide order tracking status and shipping updates.
    @GetMapping("/trackOrder")
    public ResponseEntity<?> trackOrder(@RequestParam("trackingNumber") String trackingNumber){ //wildcard?
        Optional<Order> order = orderService.trackOrder(trackingNumber);
        if (order.isPresent()){
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not found");
    }
}

