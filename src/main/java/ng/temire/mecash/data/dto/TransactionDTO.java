package ng.temire.mecash.data.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class TransactionDTO implements Serializable {
    private final Long id;
    private final String toAccount;
    private final String fromAccount;
    private final BigDecimal amount;
    private final String reference;
    private final Date dateCreated;
    private final Date transactionDate;
    private final String initiator;
    private final String narration;
}
