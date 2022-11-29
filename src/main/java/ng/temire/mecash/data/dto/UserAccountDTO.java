package ng.temire.mecash.data.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAccountDTO implements Serializable {
    private Long id;
    private Long userId;
    private String number;
    private String userRef;
    private String currency;
    private BigDecimal currentBalance;
    private BigDecimal availableBalance;
}
