package ng.temire.mecash.data.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrencyDTO implements Serializable {
    private String name;
    private String symbol;
}
