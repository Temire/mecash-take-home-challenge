package ng.temire.mecash.rest.response;

import lombok.*;
import ng.temire.mecash.data.dto.UserAccountDTO;
import ng.temire.mecash.data.dto.UserDTO;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    UserDTO user;
    UserAccountDTO account;
    String token;
}
