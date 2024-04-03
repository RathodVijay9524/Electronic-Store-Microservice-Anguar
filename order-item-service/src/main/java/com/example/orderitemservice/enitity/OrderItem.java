package com.example.orderitemservice.enitity;

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
    private  int orderItemId;
    private String userId;
    private String productId;
    private  int quantity;
    private  int totalPrice;




    //private  Product product;


   // private  Order order;
}
