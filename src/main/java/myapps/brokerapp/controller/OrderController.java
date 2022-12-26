package myapps.brokerapp.controller;

import myapps.brokerapp.model.User;
import myapps.brokerapp.model.enums.Instrument;
import myapps.brokerapp.model.Order;
import myapps.brokerapp.model.enums.OrderType;
import myapps.brokerapp.service.OrderService;
import myapps.brokerapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public String getAllOrders(Model model) {
        model.addAttribute("userOrders", orderService.getAllOrders());
        return "allOrders";
    }

    @GetMapping("/myOrders")
    public String getUserOrders(Model model, Principal principal) {
        User user = userService.findUserByLogin(principal.getName());
        model.addAttribute("userOrders", orderService.getUserOrdersById(user.getId()));
        return "user-orders";
    }

    @GetMapping("/create")
    public String createOrderPage(Model model, Principal principal) {
        User user = userService.findUserByLogin(principal.getName());
        model.addAttribute("order", new Order());
        model.addAttribute("account_balance", user.getAccount().getBalance());
        model.addAttribute("orderTypes", OrderType.values());
        model.addAttribute("instruments", Instrument.values());
        return "create-order";
    }

    @PostMapping("/create")
    public String createOrder(@Validated @ModelAttribute("order") Order order, Principal principal, BindingResult result) {
        if (result.hasErrors()) {
            return "create-order";
        }
        User user = userService.findUserByLogin(principal.getName());
        order.setTraderId(user.getId());
        orderService.create(order, user.getAccount());
        return "redirect:/orders/myOrders";
    }

}
