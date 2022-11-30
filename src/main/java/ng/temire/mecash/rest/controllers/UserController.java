package ng.temire.mecash.rest.controllers;


import lombok.RequiredArgsConstructor;
import ng.temire.mecash.data.dto.UserDTO;
import ng.temire.mecash.data.entity.User;
import ng.temire.mecash.rest.request.LoginVM;
import ng.temire.mecash.rest.response.GenericResponseDTO;
import ng.temire.mecash.service.UserService;
import ng.temire.mecash.service.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/signin")
  public ResponseEntity<GenericResponseDTO> login(@RequestBody LoginVM loginVM) {
    GenericResponseDTO response = userService.signin(loginVM.getUsername(), loginVM.getPassword());
    return new ResponseEntity<>(response, response.getStatus());
  }

  @PostMapping("/signup")
  public ResponseEntity<GenericResponseDTO> signup(@RequestBody UserDTO user) {
    GenericResponseDTO response = userService.signup(user);
    return new ResponseEntity<>(response, response.getStatus());
  }

}
