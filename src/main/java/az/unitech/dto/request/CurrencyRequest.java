package az.unitech.dto.request;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRequest {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;


}
