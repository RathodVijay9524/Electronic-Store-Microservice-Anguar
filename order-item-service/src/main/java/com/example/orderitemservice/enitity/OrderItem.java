package com.example.orderitemservice.enitity;

import com.vijay.commonservice.order.model.OrderDto;
import com.vijay.commonservice.product.model.ProductResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document("order_items")
public class OrderItem {

    @Id
    private  String orderItemId;
    private String userId;
    private String orderId;
    private String productId;
    private  int quantity;
    private  int totalPrice;
    private OrderDto order;
    private ProductResponse product;

}
