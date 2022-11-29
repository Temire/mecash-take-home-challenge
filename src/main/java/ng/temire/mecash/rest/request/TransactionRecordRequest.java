package ng.temire.mecash.rest.request;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRecordRequest {
    String number;
    Date to;
    Date from;

}
