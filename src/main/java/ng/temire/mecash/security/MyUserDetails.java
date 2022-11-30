package ng.temire.mecash.security;

import lombok.RequiredArgsConstructor;
import ng.temire.mecash.data.dto.UserDTO;
import ng.temire.mecash.data.entity.User;
import ng.temire.mecash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    @Lazy
    @Autowired
    UserService service;

    User appUser;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   UserDTO retrievedUser = service.getAppUser(username);

    if (retrievedUser == null) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    return org.springframework.security.core.userdetails.User//
        .withUsername(username)//
        .password(retrievedUser.getPassword())//
        .authorities(retrievedUser.getRoles())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
