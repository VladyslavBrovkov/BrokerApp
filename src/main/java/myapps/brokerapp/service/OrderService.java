package myapps.brokerapp.service;

import myapps.brokerapp.model.Account;
import myapps.brokerapp.model.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order, Account account);

    List<Order> getAllOrders();

    List<Order> getUserOrdersById(Integer id);

}
