package ng.temire.mecash.data.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDTO implements Serializable {
    private Long id;
    private String toAccount;
    private String fromAccount;
    private BigDecimal amount;
    private String reference;
    private Date dateCreated;
    private Date transactionDate;
    private String initiator;
    private String narration;
}
