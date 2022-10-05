package tacos.controllers.web_controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.artemis.OrderMessagingService;
import tacos.data.OrderRepository;
import tacos.entity.TacoOrder;
import tacos.entity.User;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

	private OrderRepository orderRepo;
	private OrderMessagingService messageService;
	
	@Autowired
	public OrderController(OrderRepository orderRepo, OrderMessagingService messageService) {
		this.orderRepo = orderRepo;
		this.messageService = messageService;
	}

	@GetMapping("/current")
	public String orderForm() {
		
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(
			@Valid TacoOrder order, Errors errors,
			SessionStatus sessionStatus,
			Authentication authentication) {
		
		if(errors.hasErrors())
			return "orderForm";

		User user = (User)authentication.getPrincipal();
		order.setUser(user);

		orderRepo.save(order);
		messageService.sendOrder(order);

		sessionStatus.isComplete();
		
		return "redirect:/";
	}
}
