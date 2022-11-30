package ng.temire.mecash.rest.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountBalanceResponse implements Serializable {
    String account;
    BigDecimal currentBalance, availableBalance;
}
