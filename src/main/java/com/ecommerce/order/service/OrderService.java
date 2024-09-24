package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.entity.ORDER_STATUS;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.PAYMENT_STATUS;
import com.ecommerce.order.dto.ProductDTO;
import com.ecommerce.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private ObjectMapper objectMapper;
//    @Autowired
//    RestTemplate restTemplate;

    public Order createOrder(OrderRequest orderRequest) {
       //User user =  restTemplate.getForObject("http://USER-SERVICE/user/"+userId, User.class);

        String productsJson = null;
        try {
            // Serialize the list of products to a JSON string
            productsJson = objectMapper.writeValueAsString(orderRequest.getProducts());
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

       Order order = new Order(orderRequest.getUserId()
                ,LocalDateTime.now()
                ,productsJson
                ,orderRequest.getProducts().stream().map(ProductDTO::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add)
                ,"adress" /*user.getAddress() */
                ,ORDER_STATUS.PENDING
                ,PAYMENT_STATUS.PENDING
               , UUID.randomUUID().toString()
        );
      //  System.out.println("Order : " + order.toString());

        return orderRepository.save(order);
    }

    public Optional<Order> getOrderByID(int id) {
        Optional<Order> byId = orderRepository.findById((long) id);
        return byId;
    }

    public List<Order> getAllOrdersForUser(String userId) {
        return orderRepository.findAllByUserId(userId); //custom query!!
    }

    public Order updateOrderStatus(long orderId) {
        Optional<Order> byId = orderRepository.findById(orderId);
        if (byId.isPresent()){
            byId.get().setOrderStatus(ORDER_STATUS.SHIPPED);
            orderRepository.save(byId.get());
            return byId.get();
        }
        else return null;
    }

    public boolean cancelOrder(int orderId) { //return details why cannot cancel
        Optional<Order> byId = orderRepository.findById((long)orderId);
            if (byId.isPresent() && byId.get().getOrderStatus() == ORDER_STATUS.PENDING ){
                    byId.get().setOrderStatus(ORDER_STATUS.CANCELLED);
                    orderRepository.save(byId.get());
                    return true;
            }
            else return false;
    }

    public Optional<Order> trackOrder(String trackingNumber) {
        Optional<Order> byTrackingNumber = orderRepository.findByTrackingNumber(trackingNumber);
        return byTrackingNumber;
    }
}
