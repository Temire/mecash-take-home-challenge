package ng.temire.mecash.rest.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionRequest {
    String to, from, narration;
    double amount;
}
