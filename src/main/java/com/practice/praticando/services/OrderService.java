package com.practice.praticando.services;


import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.praticando.dtos.OrderDTO;
import com.practice.praticando.dtos.OrderItemDTO;
import com.practice.praticando.entities.Order;
import com.practice.praticando.entities.OrderItem;
import com.practice.praticando.entities.OrderStatus;
import com.practice.praticando.entities.Product;
import com.practice.praticando.entities.User;
import com.practice.praticando.repositories.OrderItemRepository;
import com.practice.praticando.repositories.OrderRepository;
import com.practice.praticando.repositories.ProductRepository;
import com.practice.praticando.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Recurso não encontrado."));
		authService.validateSelfOrAdmin(order.getClient().getId());
		return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO insert (OrderDTO dto) {
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user);
		
		for (OrderItemDTO itemDto : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDto.getproductId());
			OrderItem item = new OrderItem(itemDto.getQuantity(), product.getPrice(), order, product);
			order.getItems().add(item);
		}
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
	
	
}
