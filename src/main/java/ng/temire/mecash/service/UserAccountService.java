package ng.temire.mecash.service;

import ng.temire.mecash.data.dto.UserAccountDTO;
import ng.temire.mecash.data.dto.UserDTO;
import ng.temire.mecash.data.entity.UserAccount;
import ng.temire.mecash.data.repository.UserAccountRepository;
import ng.temire.mecash.rest.response.AccountBalanceResponse;
import ng.temire.mecash.rest.response.GenericResponseDTO;
import ng.temire.mecash.service.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class UserAccountService {

    final UserAccountRepository userAccountRepository;
    final UserAccountMapper userAccountMapper;

    @Value("${currency.A}")
    String currencyA;
    @Value("${currency.A}")
    String currencyB;


    public UserAccountService(UserAccountRepository userAccountRepository, UserAccountMapper userAccountMapper) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountMapper = userAccountMapper;
    }

    public UserAccountDTO getAccountByNumber(String number) {
        Optional<UserAccount> userAccount = userAccountRepository.findByNumber(number);
        if (userAccount.isPresent())
            return userAccountMapper.toDto(userAccount.get());
        else
            return null;
    }

    public UserAccountDTO getAccountByUserId(long number) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUserId(number);
        if (userAccount.isPresent())
            return userAccountMapper.toDto(userAccount.get());
        else
            return null;
    }

    public GenericResponseDTO getAccountBalance(String number) {
        Optional<UserAccount> userAccount = userAccountRepository.findByNumber(number);
        if (userAccount.isPresent()){
            AccountBalanceResponse response = new AccountBalanceResponse();
            response.setAccount(number);
            response.setAvailableBalance(userAccount.get().getAvailableBalance());
            response.setCurrentBalance(userAccount.get().getCurrentBalance());
            return new GenericResponseDTO("00", HttpStatus.OK, "Account balances returned!.", response);
        }
        else
            return new GenericResponseDTO("99", HttpStatus.NOT_FOUND, "Account not found!.", null);
    }

    public UserAccountDTO save(UserAccountDTO accountDTO) {
        return userAccountMapper.toDto(userAccountRepository.save(userAccountMapper.toEntity(accountDTO)));
    }

    public UserAccountDTO createAccount(UserDTO user) {
        UserAccountDTO accountDTO = new UserAccountDTO();
        List<String> acctVals = accountAvailable();
        if(acctVals.get(0).equalsIgnoreCase("OK")) {
            accountDTO.setCurrency(acctVals.get(1));
            accountDTO.setUserId(user.getId());
            accountDTO.setUserRef(acctVals.get(1)+"-/" + user.getId());
            accountDTO.setNumber(acctVals.get(2));
            accountDTO.setAvailableBalance(new BigDecimal(Double.valueOf(acctVals.get(3))));
            accountDTO.setCurrentBalance(new BigDecimal(Double.valueOf(acctVals.get(3))));
            return save(accountDTO);
        }
        else return null;
}


    public boolean existsByNumber(String number) {
        return userAccountRepository.existsByNumber(number);
    }

    public List<String> accountAvailable() {
        List<String> l = null;
        int rand = new Random().nextInt(10 - 1 + 1) + 1;
        if (rand % 2 == 0) {
            if (!existsByNumber(currencyA)) {
                l = new ArrayList<>();
                l.add("OK");
                l.add("A");
                l.add(currencyA);
                int balance = rand * 19545;
                l.add("" + balance);
            } else if (!existsByNumber(currencyB)) {
                l = new ArrayList<>();
                l.add("OK");
                l.add("A");
                l.add(currencyA);
                int balance = rand * 19545;
                l.add("" + balance);
            } else {
                l = new ArrayList<>();
                l.add("N/A");
            }
        } else {
            if (!existsByNumber(currencyB)) {
                l = new ArrayList<>();
                l.add("OK");
                l.add("A");
                l.add(currencyA);
                int balance = rand * 19545;
                l.add("" + balance);
            } else if (!existsByNumber(currencyA)) {
                l = new ArrayList<>();
                l.add("OK");
                l.add("A");
                l.add(currencyA);
                int balance = rand * 19545;
                l.add("" + balance);
            } else {
                l = new ArrayList<>();
                l.add("N/A");
            }
        }
        return l;
    }
}
