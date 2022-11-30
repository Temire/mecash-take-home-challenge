package ng.temire.mecash.rest.controllers;

import lombok.RequiredArgsConstructor;
import ng.temire.mecash.data.dto.UserAccountDTO;
import ng.temire.mecash.data.entity.UserAccount;
import ng.temire.mecash.rest.response.GenericResponseDTO;
import ng.temire.mecash.service.UserAccountService;
import ng.temire.mecash.service.mapper.UserAccountMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userService;
    private final UserAccountMapper userMapper;

    @PostMapping("/user/{number}")
    public ResponseEntity<GenericResponseDTO> getAccount(@PathVariable String number) {
        UserAccountDTO accountDTO = userService.getAccountByNumber(number);
        if(ObjectUtils.isEmpty(accountDTO))
            return new ResponseEntity<>(new GenericResponseDTO("99", HttpStatus.NO_CONTENT, "Account not found!.", null),HttpStatus.EXPECTATION_FAILED );
        else return new ResponseEntity<>(new GenericResponseDTO("00", HttpStatus.OK, "Account retrieved successfullly!.", accountDTO),HttpStatus.EXPECTATION_FAILED );
    }

    @PostMapping("/user/balance/{number}")
    public ResponseEntity<GenericResponseDTO> getBalance(@PathVariable String number) {
        GenericResponseDTO response = userService.getAccountBalance(number);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
