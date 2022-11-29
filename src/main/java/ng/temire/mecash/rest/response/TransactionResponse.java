package ng.temire.mecash.rest.response;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionResponse {
    String senderName, beneficiaryName, narration, reference, senderAccount, beneficiaryAccount, status, message;
    Date transactionDate;

}
