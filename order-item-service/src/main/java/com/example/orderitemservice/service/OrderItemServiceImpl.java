package com.example.orderitemservice.service;

import com.example.orderitemservice.enitity.OrderItem;
import com.example.orderitemservice.repository.OrderItemRepository;
import com.vijay.commonservice.order.model.OrderItemDto;
import com.vijay.commonservice.user.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItemDto addOrderItem(OrderItemDto req) {
        OrderItem orderItem = OrderItem.builder()
                .orderItemId(req.getOrderItemId())
                .quantity(req.getQuantity())
                .totalPrice(req.getTotalPrice())
                .productId(req.getProductId())
                .userId(req.getUserId())
                .build();

       orderItemRepository.save(orderItem);

        return OrderItemDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .quantity(orderItem.getQuantity())
                .productId(orderItem.getProductId())
                .userId(orderItem.getUserId())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    @Override
    public OrderItemDto getOrderItemByOrderItemId(String orderItemId) {
        OrderItem orderItem= orderItemRepository.findById(orderItemId).orElseThrow(() -> new ResourceNotFoundException("OrderItem", "Id", orderItemId));

        return OrderItemDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProductId())
                .userId(orderItem.getUserId())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(String orderId) {
        List<OrderItem> OtderItemList= orderItemRepository.findByOrderId(orderId);

        List<OrderItemDto> listOrderItems= OtderItemList.stream().map(orderItem -> {
            OrderItemDto orderItemDto = new OrderItemDto();
            BeanUtils.copyProperties(orderItem, orderItemDto);
            return orderItemDto;
        }).collect(Collectors.toList());

        return listOrderItems;
    }

   /*  return orderItemList.stream()
             .map(this::convertToDto)
            .collect(Collectors.toList());*/

    @Override
    public void deleteOrderItemByOrderItemId(String orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public void deleteOrderItemsByOrderId(String orderId) {
        List<OrderItem> byOrderId= orderItemRepository.findByOrderId(orderId);
        orderItemRepository.deleteAll(byOrderId);
    }

    @Override
    public OrderItemDto updateOrderItem(String orderItemId, OrderItemDto req) {
        // Retrieve the existing orderItem from the repository
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "Id", orderItemId));

        // Update the properties of the existing orderItem
        orderItem.setUserId(req.getUserId());
        orderItem.setProductId(req.getProductId());
        orderItem.setQuantity(req.getQuantity());
        orderItem.setTotalPrice(req.getTotalPrice());

        // Save the updated orderItem
        orderItemRepository.save(orderItem);

        // Convert the updated orderItem to DTO and return
        return convertToDto(orderItem);
    }



    private OrderItemDto convertToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        BeanUtils.copyProperties(orderItem, orderItemDto);
        return orderItemDto;
    }
}
