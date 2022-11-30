package ng.temire.mecash.service;

import ng.temire.mecash.data.dto.UserAccountDTO;
import ng.temire.mecash.data.dto.UserDTO;
import ng.temire.mecash.data.entity.User;
import ng.temire.mecash.data.repository.UserRepository;
import ng.temire.mecash.rest.response.AuthResponse;
import ng.temire.mecash.rest.response.GenericResponseDTO;
import ng.temire.mecash.security.JwtTokenProvider;
import ng.temire.mecash.security.constants.Role;
import ng.temire.mecash.security.exceptions.CustomException;
import ng.temire.mecash.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    UserAccountService accountService;

    public UserService(UserMapper mapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public UserDTO getAppUser(String username) {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            UserDTO userDTO = mapper.toDto(user.get());
            userDTO.setRoles(Arrays.asList(Role.ROLE_CLIENT));
            return userDTO;
        } else
            return null;
    }

    public UserDTO getUserById(long username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            UserDTO userDTO = mapper.toDto(user.get());
            userDTO.setRoles(Arrays.asList(Role.ROLE_CLIENT));
            return userDTO;
        } else
            return null;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserDTO save(User user) {
        UserDTO save = mapper.toDto(userRepository.save(user));
        save.setRoles(Arrays.asList(Role.ROLE_CLIENT));
        return save;
    }

    public GenericResponseDTO signin(String username, String password) {
        try {
            if (existsByUsername(username)) {
                Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                if (auth.isAuthenticated()) {
                    UserDTO userDTO = getAppUser(username);
                    String token = jwtTokenProvider.createToken(username, userDTO.getRoles());
                    AuthResponse authResponse = new AuthResponse(userDTO, accountService.getAccountByUserId(userDTO.getId()), token);
                    return new GenericResponseDTO("00", HttpStatus.OK, "User is authenticated!", authResponse);
                } else
                    return new GenericResponseDTO("99", HttpStatus.EXPECTATION_FAILED, "User not authenticated!.", null);
            } else
                return new GenericResponseDTO("99", HttpStatus.NO_CONTENT, "Error Retrieving Records", null);
        } catch (Exception ex) {
            return new GenericResponseDTO("99", HttpStatus.INTERNAL_SERVER_ERROR, "Error Retrieving Records", null);
        }
    }

    public GenericResponseDTO signup(UserDTO appUser) {
        try {
            if (!userRepository.existsByUsername(mapper.toEntity(appUser).getUsername())) {
                appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
                UserDTO userDTO = save(mapper.toEntity(appUser));

                UserAccountDTO accountDTO = accountService.createAccount(userDTO);
                if(accountDTO ==null){
                    throw new CustomException("Not Account numbers available for user account!", HttpStatus.UNPROCESSABLE_ENTITY);
                }

                String token = jwtTokenProvider.createToken(appUser.getUsername(), userDTO.getRoles());
                AuthResponse authResponse = new AuthResponse(userDTO, accountDTO, token);
                return new GenericResponseDTO("00", HttpStatus.OK, "User created Succesfully!", authResponse);
            } else {
                throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GenericResponseDTO("99", HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }
}
