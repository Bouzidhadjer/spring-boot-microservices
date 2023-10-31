package com.hadjerbouzid.orderservice.service;

import com.hadjerbouzid.orderservice.dto.InventoryResponse;
import com.hadjerbouzid.orderservice.dto.OrderLineItemsDto;
import com.hadjerbouzid.orderservice.dto.OrderRequest;
import com.hadjerbouzid.orderservice.model.Order;
import com.hadjerbouzid.orderservice.model.OrderLineItems;
import com.hadjerbouzid.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;
    public  String placeOrder(OrderRequest orderRequest){
         Order order = new Order();
         order.setOrderNumber(order.getOrderNumber());
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream()
                 .map(this::mapToDto)
                 .toList();
         order.setOrderLineItemsList(orderLineItemsList);
        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode).toList();
        //call Inventory Service , and place order if product is in
        // stock
       InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
        if(allProductsInStock) {
           orderRepository.save(order);
           return "Order Placed Successfully";
       } else {
           throw new IllegalArgumentException("Product is not in stock, please try again later");
       }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
         OrderLineItems orderLineItems = new OrderLineItems();
         orderLineItems.setPrice(orderLineItemsDto.getPrice());
         orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
         orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
         return orderLineItems;
    }
}
