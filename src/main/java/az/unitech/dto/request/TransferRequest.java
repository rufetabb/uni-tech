package az.unitech.dto.request;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferRequest {


    private String fromAccountNumber;

    private String toAccountNumber;

    private BigDecimal amount;

}
