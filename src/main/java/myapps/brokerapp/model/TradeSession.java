package myapps.brokerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeSession {
    private Boolean activeStatus = Boolean.FALSE;
    private Date startTime;
    private Date finishTime;
    private List<Agreement> agreementList = Collections.synchronizedList(new ArrayList<>());
    private List<Order> buyOrderList = Collections.synchronizedList(new ArrayList<>());
    private List<Order> sellOrderList = Collections.synchronizedList(new ArrayList<>());
}
