package myapps.brokerapp.service;

import myapps.brokerapp.model.Agreement;
import myapps.brokerapp.model.TradeSession;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SessionService {

    void processOrders() throws ExecutionException, InterruptedException;

    boolean startSession() throws ExecutionException, InterruptedException;

    boolean endSession();

    boolean isActive();

    TradeSession getSession();

    Agreement create();

    List<Agreement> getAgreementList();

    List<Agreement> getUserAgreements(Integer traderId);

    void cancelProgressOrders();

}
