package myapps.brokerapp.service.impl;

import myapps.brokerapp.model.Agreement;
import myapps.brokerapp.model.Order;
import myapps.brokerapp.model.TradeSession;
import myapps.brokerapp.model.enums.OrderStatus;
import myapps.brokerapp.service.SessionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {

    private static TradeSession tradeSession;


    SessionServiceImpl() {
        tradeSession = new TradeSession();
    }

    @Override
    public void processOrders() throws ExecutionException, InterruptedException {
        ExecutorService THREAD_POOL = Executors.newFixedThreadPool(4);
        while (isActive()) {
            CompletableFuture.supplyAsync(this::create, THREAD_POOL)
                    .thenAccept((a) -> {
                        if (a == null) {
                            return;
                        }
                        tradeSession.getAgreementList().add(a);
                    })
                    .get();
        }
        THREAD_POOL.shutdown();
    }

    @Override
    public void startSession() throws ExecutionException, InterruptedException {
        tradeSession.setActiveStatus(true);
        tradeSession.setStartTime(new Date());
        processOrders();
    }

    @Override
    public void endSession() {
        tradeSession.setFinishTime(new Date());
        tradeSession.setActiveStatus(false);
        cancelProgressOrders();
    }

    @Override
    public boolean isActive() {
        return tradeSession.getActiveStatus();
    }

    @Override
    public TradeSession getSession() {
        return tradeSession;
    }

    @Override
    public Agreement create() {
        List<Order> buyOrderList = tradeSession.getBuyOrderList();
        List<Order> sellOrderList = tradeSession.getSellOrderList();
        checkOrderExpired(buyOrderList);
        checkOrderExpired(sellOrderList);
        for (Order buyOrder : buyOrderList) {
            if (!buyOrder.getOrderStatus().equals(OrderStatus.IN_PROGRESS)) {
                continue;
            }
            Optional<Order> sellOrder = sellOrderList.stream()
                    .filter(o -> o.getInstrument().equals(buyOrder.getInstrument()) &&
                            o.getPrice().equals(buyOrder.getPrice()) &&
                            o.getQuantity().equals(buyOrder.getQuantity()) &&
                            o.getOrderStatus().equals(OrderStatus.IN_PROGRESS))
                    .findFirst();
            if (sellOrder.isPresent()) {
                Order sellOrderInstance = sellOrder.get();
                buyOrder.setOrderStatus(OrderStatus.DONE);
                sellOrderInstance.setOrderStatus(OrderStatus.DONE);
                return Agreement.createAgreement(buyOrder.getTraderId(), sellOrderInstance.getTraderId(), buyOrder.getInstrument());
            }
        }
        return null;
    }

    @Override
    public List<Agreement> getAgreementList() {
        return tradeSession.getAgreementList();
    }

    @Override
    public List<Agreement> getUserAgreements(Integer traderId) {
        return tradeSession.getAgreementList().stream()
                .filter(a -> a.getBuyOrderTraderId().equals(traderId) ||
                        a.getSellOrderTraderId().equals(traderId))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelProgressOrders() {
        tradeSession.getSellOrderList()
                .forEach(order -> {
                    if (!order.getOrderStatus().equals(OrderStatus.DONE)) {
                        order.setOrderStatus(OrderStatus.CANCELED);
                    }
                });
        tradeSession.getBuyOrderList()
                .forEach(order -> {
                    if (!order.getOrderStatus().equals(OrderStatus.DONE)) {
                        order.setOrderStatus(OrderStatus.CANCELED);
                    }
                });
    }

    public void checkOrderExpired(List<Order> orderList) {
        orderList.stream().filter(o -> o.getExpirationTime() != null &&
                        !o.getExpirationTime().after(new Date()))
                .forEach(o -> o.setOrderStatus(OrderStatus.CANCELED));
    }

}
