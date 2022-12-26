package myapps.brokerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import myapps.brokerapp.model.enums.Instrument;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agreement {
    private Instrument instrument;
    private Integer buyOrderTraderId;
    private Integer sellOrderTraderId;
    private Date agreementTime;

    public static Agreement createAgreement(Integer buyOrderTraderId, Integer sellOrderTraderId,
                                            Instrument instrument) {
        Agreement agreement = new Agreement();
        agreement.setAgreementTime(new Date());
        agreement.setBuyOrderTraderId(buyOrderTraderId);
        agreement.setSellOrderTraderId(sellOrderTraderId);
        agreement.setInstrument(instrument);
        return agreement;
    }
}
