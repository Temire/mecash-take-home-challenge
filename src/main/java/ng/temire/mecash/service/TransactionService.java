package ng.temire.mecash.service;

import ng.temire.mecash.data.dto.TransactionDTO;
import ng.temire.mecash.data.entity.Transaction;
import ng.temire.mecash.data.repository.TransactionRepository;
import ng.temire.mecash.rest.request.TransactionRecordRequest;
import ng.temire.mecash.rest.request.TransactionRequest;
import ng.temire.mecash.rest.response.GenericResponseDTO;
import ng.temire.mecash.rest.response.TransactionResponse;
import ng.temire.mecash.service.mapper.TransactionMapper;
import ng.temire.mecash.utils.ServiceUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class TransactionService {
    final TransactionMapper transactionMapper;
    final TransactionRepository transactionRepository;
    final ServiceUtils utils;


    public TransactionService(TransactionMapper transactionMapper, TransactionRepository transactionRepository, ServiceUtils utils) {
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
        this.utils = utils;
    }

    public GenericResponseDTO findUserTxns(TransactionRecordRequest request, Pageable pageable) {
        try {
            List<Transaction> t = transactionRepository.findAllByTransactionDateIsBetweenOrderByTransactionDateAsc(request.getTo(), request.getFrom(), pageable);
            if (ObjectUtils.isEmpty(t)) {
                return new GenericResponseDTO("01", HttpStatus.NO_CONTENT, "No transactions found!.", null);
            } else {
                List<TransactionDTO> txns = transactionMapper.toDto(t);
                return new GenericResponseDTO("OO", HttpStatus.OK, "Successfully retrieved Transactions!.", txns);
            }
        } catch (Exception ex) {
            return new GenericResponseDTO("99", HttpStatus.INTERNAL_SERVER_ERROR, "Error Retrieving Records", null);
        }
    }

    public GenericResponseDTO findAllTxns(TransactionRecordRequest request, Pageable pageable) {
        try {
            List<Transaction> t = transactionRepository.findByToAccountIsOrFromAccountAndTransactionDateIsBetweenOrderByTransactionDateAsc(request.getNumber(), request.getTo(), request.getFrom(), pageable);
            if (ObjectUtils.isEmpty(t)) {
                return new GenericResponseDTO("01", HttpStatus.NO_CONTENT, "No transactions found!.", null);
            } else {
                List<TransactionDTO> txns = transactionMapper.toDto(t);
                return new GenericResponseDTO("OO", HttpStatus.OK, "Successfully retrieved Transactions!.", txns);
            }
        } catch (Exception ex) {
            return new GenericResponseDTO("99", HttpStatus.INTERNAL_SERVER_ERROR, "Error Retrieving Records", null);
        }
    }

    public GenericResponseDTO send(TransactionRequest request) {
        TransactionResponse send = utils.sendAndUpdate(request);
        if(send.getStatus().equalsIgnoreCase("00"))
            return new GenericResponseDTO("OO", HttpStatus.OK, send.getMessage(), send);
        else{
            if(send.getStatus().equalsIgnoreCase("98"))
                return new GenericResponseDTO("99", HttpStatus.INTERNAL_SERVER_ERROR, send.getMessage(), send);
            else
                return new GenericResponseDTO("99", HttpStatus.EXPECTATION_FAILED, send.getMessage(), send);
        }
    }
}
