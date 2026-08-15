package org.tarun.ecombackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tarun.ecombackend.model.Order;
import org.tarun.ecombackend.model.OrderItem;
import org.tarun.ecombackend.model.Product;
import org.tarun.ecombackend.model.dto.OrderItemRequest;
import org.tarun.ecombackend.model.dto.OrderItemResponse;
import org.tarun.ecombackend.model.dto.OrderRequest;
import org.tarun.ecombackend.model.dto.OrderResponse;
import org.tarun.ecombackend.repo.OrderRepo;
import org.tarun.ecombackend.repo.ProductRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    @Autowired
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Autowired
    public void setOrderRepo(OrderRepo orderRepo){
        this.orderRepo=orderRepo;
    }

    public OrderResponse placOrder(OrderRequest orderRequest) {
        Order order = new Order();
        String orderId = "ORD"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomerName(orderRequest.customerName());
        order.setEmail(orderRequest.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest req: orderRequest.items()){
            Product product = productRepo.findById(req.productId()).orElseThrow(
                    ()->new RuntimeException("Product Not Found"));

            product.setStockQuantity(product.getStockQuantity()-req.quantity());
            productRepo.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(req.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(req.quantity())))
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for(OrderItem item: order.getOrderItems()){
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getTotalPrice());

            orderItemResponses.add(orderItemResponse);
        }

        return new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                orderItemResponses);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrderResponses() {
        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> responses = new ArrayList<>();

        for(Order order: orders){

            List<OrderItemResponse> orderItemResponses = new ArrayList<>();
            for(OrderItem orderItem: order.getOrderItems()){
                OrderItemResponse orderResponse = new OrderItemResponse(
                        orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getTotalPrice());

                orderItemResponses.add(orderResponse);
            }
            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    orderItemResponses);

            responses.add(orderResponse);
        }

        return responses;
    }
}
