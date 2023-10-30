package com.hadjerbouzid.orderservice.service;

import com.hadjerbouzid.orderservice.dto.OrderLineItemsDto;
import com.hadjerbouzid.orderservice.dto.OrderRequest;
import com.hadjerbouzid.orderservice.model.Order;
import com.hadjerbouzid.orderservice.model.OrderLineItems;
import com.hadjerbouzid.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    public  void placeOrder(OrderRequest orderRequest){
         Order order = new Order();
         order.setOrderNumber(order.getOrderNumber());
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream()
                 .map(this::mapToDto)
                 .toList();
         order.setOrderLineItemsList(orderLineItemsList);
         orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
         OrderLineItems orderLineItems = new OrderLineItems();
         orderLineItems.setPrice(orderLineItemsDto.getPrice());
         orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
         orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
         return orderLineItems;
    }
}
