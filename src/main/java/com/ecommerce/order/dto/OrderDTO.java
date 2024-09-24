package com.ecommerce.order.dto;

import com.ecommerce.order.entity.ORDER_STATUS;
import com.ecommerce.order.entity.PAYMENT_STATUS;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer orderId;
    private String userId;                  //user email address
    private LocalDateTime orderDateTime;    //when order placed
    //private List<ProductDTO> purchaseList;
    @Column(name = "products", columnDefinition = "TEXT")
    private String purchasesJson;
    private BigDecimal totalAmount;         //total amount
    private String shippingAddress;

    private ORDER_STATUS orderStatus;
    private PAYMENT_STATUS paymentStatus;
    private String trackingNumber;

}
