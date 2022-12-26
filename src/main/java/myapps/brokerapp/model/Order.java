package myapps.brokerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import myapps.brokerapp.model.enums.Instrument;
import myapps.brokerapp.model.enums.OrderStatus;
import myapps.brokerapp.model.enums.OrderType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private OrderStatus orderStatus;
    private OrderType orderType;
    private BigDecimal price;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date orderTime;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date expirationTime;
    private Instrument instrument;
    private Double quantity;
    private Integer traderId;
}
