package com.ecommerce.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String userId;                  //user email address
    private LocalDateTime orderDateTime;    //when order placed

    @Lob
    @Column(name = "products", columnDefinition = "TEXT")
    private String purchasesJson;  // Store the product list as a JSON string
    private BigDecimal totalAmount;         //total amount
    private String shippingAddress;
    @Enumerated(EnumType.STRING)
    private ORDER_STATUS orderStatus;
    @Enumerated(EnumType.STRING)
    private PAYMENT_STATUS paymentStatus;
    private String trackingNumber;

    public Order(String userId, LocalDateTime now, String purchasesJson, BigDecimal totalAmount, String adress, ORDER_STATUS orderStatus, PAYMENT_STATUS paymentStatus, String trackingNumber) {
        this.userId=userId;
        this.orderDateTime=now;
        this.purchasesJson=purchasesJson;
        this.totalAmount=totalAmount;
        this.shippingAddress=adress;
        this.orderStatus=orderStatus;
        this.paymentStatus=paymentStatus;
        this.trackingNumber=trackingNumber;
    }
}
