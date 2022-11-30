package ng.temire.mecash.data.dto;

import lombok.*;
import ng.temire.mecash.security.constants.Role;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private List<Role> roles;


    public String fullname(){
        return first_name +" "+last_name;
    }
}
