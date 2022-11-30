package ng.temire.mecash.rest.request;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginVM implements Serializable {
    String username, password;
}
