package myapps.brokerapp.service.impl;

import myapps.brokerapp.exception.NotActiveSessionException;
import myapps.brokerapp.exception.NotEnoughMoneyOnAccount;
import myapps.brokerapp.model.Account;
import myapps.brokerapp.model.Order;
import myapps.brokerapp.model.enums.OrderStatus;
import myapps.brokerapp.model.enums.OrderType;
import myapps.brokerapp.model.TradeSession;
import myapps.brokerapp.service.AccountService;
import myapps.brokerapp.service.OrderService;
import myapps.brokerapp.service.SessionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final SessionService sessionService;

    private final AccountService accountService;

    public OrderServiceImpl(SessionService sessionService, AccountService accountService) {
        this.sessionService = sessionService;
        this.accountService = accountService;
    }

    @Override
    public Order create(Order order, Account account) {
        if (!sessionService.isActive()) {
            throw new NotActiveSessionException("Session is not active");
        } else if (!checkingAccountBalance(order, account)) {
            throw new NotEnoughMoneyOnAccount("Not enough money");
        }
        TradeSession currentSession = sessionService.getSession();
        order.setOrderTime(new Date());
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        if (order.getOrderType().equals(OrderType.BUY)) {
            currentSession.getBuyOrderList().add(order);
        } else if (order.getOrderType().equals(OrderType.SELL)) {
            currentSession.getSellOrderList().add(order);
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> totalList = new ArrayList<>();
        totalList.addAll(sessionService.getSession().getSellOrderList());
        totalList.addAll(sessionService.getSession().getBuyOrderList());
        return totalList;
    }

    @Override
    public List<Order> getUserOrdersById(Integer id) {
        List<Order> totalList = new ArrayList<>();
        sessionService.getSession().getSellOrderList().stream()
                .filter(or -> or.getTraderId().equals(id))
                .forEach(totalList::add);
        sessionService.getSession().getBuyOrderList().stream()
                .filter(or -> or.getTraderId().equals(id))
                .forEach(totalList::add);
        return totalList;
    }

    public boolean checkingAccountBalance(Order order, Account account) {
        BigDecimal orderTotalPrice = order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
        if (account.getBalance().compareTo(orderTotalPrice) >= 0) {
            accountService.withdrawMoney(account, orderTotalPrice);
            return true;
        }
        return false;
    }
}
