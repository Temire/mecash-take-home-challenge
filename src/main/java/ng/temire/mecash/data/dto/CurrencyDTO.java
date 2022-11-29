package ng.temire.mecash.data.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrencyDTO implements Serializable {
    private final String name;
    private final String symbol;
}
