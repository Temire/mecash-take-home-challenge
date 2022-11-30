package ng.temire.mecash.utils;

import ng.temire.mecash.data.dto.UserAccountDTO;
import ng.temire.mecash.data.entity.Transaction;
import ng.temire.mecash.data.repository.TransactionRepository;
import ng.temire.mecash.rest.request.TransactionRequest;
import ng.temire.mecash.rest.response.TransactionResponse;
import ng.temire.mecash.service.UserAccountService;
import ng.temire.mecash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static java.lang.System.out;

@Component
public class ServiceUtils {

    @Autowired
    UserAccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    TransactionRepository transactionRepository;


    public boolean canBeDebited(UserAccountDTO accountDTO, double amount) {
            double balance = accountDTO.getAvailableBalance().doubleValue();
            return balance > amount;
        }

    public TransactionResponse sendAndUpdate(TransactionRequest request) {
        TransactionResponse sent = new TransactionResponse();
        UserAccountDTO toAccount = accountService.getAccountByNumber(request.getTo());
        UserAccountDTO fromAccount = accountService.getAccountByNumber(request.getFrom());
        if (canBeDebited(fromAccount, request.getAmount())) {
            if (toAccount.getCurrency().equalsIgnoreCase(fromAccount.getCurrency())) {
                boolean fromDone = updateBalances(fromAccount, request.getAmount(), "DR");
                if (fromDone) {
                    boolean toDone = updateBalances(toAccount, request.getAmount(), "CR");
                    if (!toDone) {
                        reverse(fromAccount, request.getAmount());
                        sent.setNarration("");
                        sent.setSenderAccount(fromAccount.getNumber());
                        sent.setSenderName(userService.getUserById(fromAccount.getUserId()).fullname());
                        sent.setBeneficiaryAccount(toAccount.getNumber());
                        sent.setBeneficiaryName(userService.getUserById(toAccount.getUserId()).fullname());
                        sent.setStatus("91");
                        sent.setMessage("Destination Account was not credited. Transaction rolled back!");
                    }
                    else {
                        try {
                            Transaction transaction = new Transaction();
                            transaction.setAmount(new BigDecimal(request.getAmount()));
                            transaction.setNarration(request.getNarration());
                            transaction.setTransactionDate(LocalDate.now());
                            transaction.setFromAccount(request.getFrom());
                            transaction.setInitiator("USER");
                            transaction.setToAccount(request.getTo());
                            transaction.setDateCreated(LocalDate.now());
                            transaction.setReference(generateAgentRef());
                            transaction.setToCurrency(toAccount.getCurrency());
                            transaction.setFromCurrency(fromAccount.getCurrency());
                            transactionRepository.save(transaction);

                            sent.setNarration(request.getNarration());
                            sent.setSenderAccount(fromAccount.getNumber());
                            sent.setSenderName(userService.getUserById(fromAccount.getUserId()).fullname());
                            sent.setBeneficiaryAccount(toAccount.getNumber());
                            sent.setBeneficiaryName(userService.getUserById(toAccount.getUserId()).fullname());
                            sent.setStatus("00");
                            sent.setMessage("Fund Transferred Successfully!");
                        }catch (Exception ex){
                            ex.printStackTrace();
                            sent.setNarration(ex.getMessage());
                            sent.setStatus("98");
                            sent.setMessage("Destination Account was not credited. Transaction rolled back!");
                        }
                    }
                }
            }
            else{
                CurrencyConverter converter = new CurrencyConverter();
                double toAmount = 0.0;
                boolean fromDone = updateBalances(fromAccount, request.getAmount(), "DR");
                if (fromDone) {
                    toAmount = converter.convert(fromAccount.getCurrency(), toAccount.getCurrency(),request.getAmount()).doubleValue();
                    boolean toDone = updateBalances(toAccount, toAmount , "CR");
                    if (!toDone) {
                        reverse(fromAccount, request.getAmount());
                        sent.setNarration("");
                        sent.setSenderAccount(fromAccount.getNumber());
                        sent.setSenderName(userService.getUserById(fromAccount.getUserId()).fullname());
                        sent.setBeneficiaryAccount(toAccount.getNumber());
                        sent.setBeneficiaryName(userService.getUserById(toAccount.getUserId()).fullname());
                        sent.setStatus("91");
                        sent.setMessage("Destination Account was not credited. Transaction rolled back!");
                    }
                    else {
                        try {
                            Transaction transaction = new Transaction();
                            transaction.setAmount(new BigDecimal(request.getAmount()));
                            transaction.setConvertedAmount(new BigDecimal(toAmount));
                            transaction.setNarration(request.getNarration());
                            transaction.setTransactionDate(LocalDate.now());
                            transaction.setFromAccount(request.getFrom());
                            transaction.setInitiator("USER");
                            transaction.setToAccount(request.getTo());
                            transaction.setDateCreated(LocalDate.now());
                            transaction.setReference(generateAgentRef());
                            transaction.setToCurrency(toAccount.getCurrency());
                            transaction.setFromCurrency(fromAccount.getCurrency());
                            transactionRepository.save(transaction);

                            sent.setNarration(request.getNarration());
                            sent.setSenderAccount(fromAccount.getNumber());
                            sent.setSenderName(userService.getUserById(fromAccount.getUserId()).fullname());
                            sent.setBeneficiaryAccount(toAccount.getNumber());
                            sent.setBeneficiaryName(userService.getUserById(toAccount.getUserId()).fullname());
                            sent.setStatus("00");
                            sent.setMessage("Fund Transferred Successfully!");
                        }catch (Exception ex){
                            ex.printStackTrace();
                            sent.setNarration(ex.getMessage());
                            sent.setStatus("98");
                            sent.setMessage("Destination Account was not credited. Transaction rolled back!");
                        }
                    }
                }
            }
        }
        return sent;
    }

    public boolean updateBalances(UserAccountDTO accountDTO, double amount, String type) {
        boolean updated = false;
        try {
            if (type.equalsIgnoreCase("CR")) {
                double balance = accountDTO.getAvailableBalance().doubleValue();
                accountDTO.setAvailableBalance(new BigDecimal(balance + amount));
                accountDTO.setCurrentBalance(new BigDecimal(balance + amount));
                UserAccountDTO save = accountService.save(accountDTO);
                out.println("Saved account: " + save);
                updated = true;
            } else if (type.equalsIgnoreCase("DR")) {
                double balance = accountDTO.getAvailableBalance().doubleValue();
                accountDTO.setAvailableBalance(new BigDecimal(balance - amount));
                accountDTO.setCurrentBalance(new BigDecimal(balance - amount));
                UserAccountDTO save = accountService.save(accountDTO);
                out.println("Saved account: " + save);
                updated = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            updated = false;
        }
        return updated;
    }

    public void reverse(UserAccountDTO accountDTO, double amount) {
        boolean updated = updateBalances(accountDTO, amount, "CR");
        out.println("Reversal : " + updated);
    }

    public String generateAgentRef() {
        long decimalNumber = System.nanoTime();
        String strBaseDigits = "0123456789";
        String invoiceID = "";
        int mod = 0;
        while (decimalNumber != 0) {
            mod = (int) (decimalNumber % 10);
            invoiceID = strBaseDigits.substring(mod, mod + 1) + invoiceID;
            decimalNumber = decimalNumber / 10;
        }
        boolean exists = transactionRepository.existsByReference(invoiceID);
        if(exists) generateAgentRef();

        return invoiceID;
    }

}
